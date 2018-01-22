package edu.uw.citw.service.model.impl;

import edu.uw.citw.model.SimpleModelInfo;
import edu.uw.citw.persistence.domain.ModelData;
import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.service.model.ModelService;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.weka.WekaModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private static final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private static final String MODELS_LABEL = "models";

    private static final String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss";

    private JsonNodeAdapter jsonAdapter;
    private ModelDataRepository modelRepository;
    private WekaModelUtil modelUtil;

    @Autowired
    public ModelServiceImpl(JsonNodeAdapter jsonAdapter, ModelDataRepository modelRepository, WekaModelUtil modelUtil) {
        this.jsonAdapter = jsonAdapter;
        this.modelRepository = modelRepository;
        this.modelUtil = modelUtil;
    }

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
    public String switchModel(Long id) {
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

    public void reTrainNewModel() {
        // get all training samples
        // submit milliseconds, videos to python
        // python script creates ARFF samples
        // use WEKA API to generate model
        // save model, arff, user, time, inUse=false to database
    }


    @Override
    public String retrainNewModel() {

        return null;
    }
}
