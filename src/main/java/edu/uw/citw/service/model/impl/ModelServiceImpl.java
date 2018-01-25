package edu.uw.citw.service.model.impl;

import edu.uw.citw.model.RetrainSampleFile;
import edu.uw.citw.model.RetrainSampleInstance;
import edu.uw.citw.model.SimpleModelInfo;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.ModelData;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.service.model.ModelService;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.pylaughfinder.PyLaughFinderUtil;
import edu.uw.citw.util.weka.WekaModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.lazy.IBk;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ModelServiceImpl implements ModelService {

    private static final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private static final String MODELS_LABEL = "models";
    private static final String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss";

    private JsonNodeAdapter jsonAdapter;
    private ModelDataRepository modelRepository;
    private WekaModelUtil modelUtil;
    private LaughterInstanceRepository instanceRepository;
    private AudioVideoMappingRepository avRepository;
    private PyLaughFinderUtil pythonUtil;

    @Autowired
    public ModelServiceImpl(
            JsonNodeAdapter jsonAdapter,
            ModelDataRepository modelRepository,
            WekaModelUtil modelUtil,
            LaughterInstanceRepository instanceRepository,
            AudioVideoMappingRepository avRepository,
            PyLaughFinderUtil pythonUtil) {
        this.jsonAdapter = jsonAdapter;
        this.modelRepository = modelRepository;
        this.modelUtil = modelUtil;
        this.instanceRepository = instanceRepository;
        this.avRepository = avRepository;
        this.pythonUtil = pythonUtil;
    }

    /**
     * Used to populate a dropdown list in the UI. Should make the DB smaller so it's not
     *   returning a bunch of giant model bianaries from the DB and then removing them...
     *
     * TODO: Make a view in the database that excludes the binary and ARFF data. Then read from that.
     */
    @Override
    public String getAllModels() {
        List<SimpleModelInfo> result = new ArrayList<>();

        List<ModelData> models = modelRepository.getAll();

        // for each model, strip out heavy model binary and prettify some of the output
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        for (ModelData model : models) {
            String formattedDate = sdf.format(model.getCreatedDate());

            SimpleModelInfo modelInfo = new SimpleModelInfo(
                    model.getId(),
                    formattedDate,
                    model.getCreatedBy(),
                    model.isInUse()
            );

            result.add(modelInfo);
        }

        return jsonAdapter.createJsonArray(MODELS_LABEL, result);
    }

    @Override
    public String switchModel(@Nonnull Long id) {
        // TODO: some error handling here

        // get current model in use
        ModelData currentlyInUse = modelRepository.findByInUse(true).get(0);
        currentlyInUse.setInUse(false);

        // get target model
        ModelData target = modelRepository.findById(id).get(0);
        target.setInUse(true);

        modelRepository.save(Arrays.asList(currentlyInUse, target));

        return "{\"message\":\"success\"}";
    }

    @Override
    public void retrainNewModel() {
        // TODO: maybe just create a DB view that creates JSON per every update?
        // get all training samples
        String json = getReTrainSamplesAsJson();

        // submit milliseconds & videos to python, python script creates ARFF samples
        String arff = "";
        try {
            arff = pythonUtil.runReTrainingScript(json);
        } catch (IOException e) {
            log.error("Unable to create the ARFF file. Exiting process.", e);
            return;
        }

        // use WEKA API to generate model
        ByteArrayOutputStream bytes = null;
        try {
            IBk model = modelUtil.classifyAndGetModel(new ByteArrayInputStream(arff.getBytes()));
            bytes = getModelOutput(model);
        } catch (Exception e) {
            log.error("Unable to create the model. Exiting process.", e);
        }

        // save model, arff, user, time, inUse=false to database
        ModelData newModel = new ModelData();
        assert bytes != null;
        newModel.setModelBinary(bytes.toByteArray());
        newModel.setArffData(arff);
        newModel.setCreatedDate(new Date(Calendar.getInstance().getTimeInMillis()));
        newModel.setCreatedBy("default");
        newModel.setInUse(false);

        modelRepository.save(newModel);
    }


    protected ByteArrayOutputStream getModelOutput(IBk model) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(model);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            log.error("Unable to serialize model.", e);
            return null;
        }
        return baos;
    }


    protected String getReTrainSamplesAsJson() {
        StringBuilder result = new StringBuilder();

        // get instances and map them
        List<LaughterInstance> instances = instanceRepository.getAllMarkedForRetraining();
        Map<Long, List<LaughterInstance>> instanceMap = new HashMap<>();
        for (LaughterInstance instance : instances) {
            if (instanceMap.containsKey(instance.getS3Key())) {
                instanceMap.get(instance.getS3Key()).add(instance);
            } else {
                List<LaughterInstance> list = new ArrayList<>();
                list.add(instance);
                instanceMap.put(instance.getS3Key(), list);
            }
        }

        // Get list of assets
        List<AudioVideoMapping> assets = new ArrayList<>();
        for (Long key : instanceMap.keySet()) {
            assets.addAll(avRepository.findById(key.intValue()));
        }

        // For every asset, model data and add instances.
        result.append("{\"files\": [");
        for (AudioVideoMapping asset : assets) {
            List<LaughterInstance> list = instanceMap.get(asset.getId());
            List<RetrainSampleInstance> samples = new ArrayList<>();

            for (LaughterInstance instance : list) {
                samples.add(new RetrainSampleInstance(instance.getStartTime(), instance.getStopTime(), instance.getAlgCorrect()));
            }

            RetrainSampleFile file = new RetrainSampleFile(
                    asset.getBucket(),
                    asset.getAudioFile(),
                    samples
            );

            result.append(file.toString()).append(",");
        }
        result.append("]}");

        return result.toString();
    }
}
