package edu.uw.citw.service.instance.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.model.RetrainSampleFile;
import edu.uw.citw.model.RetrainSampleUrlData;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.Tag;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.TagsRepository;
import edu.uw.citw.service.instance.InstanceService;
import edu.uw.citw.util.JsonNodeAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.*;

/**
 * For getting and modifying instances.
 *
 * Created by miles on 3/5/17.
 */
@Service
public class InstanceServiceImpl implements InstanceService {

    private static final String ALG_CORRECT = "algCorrect";
    private static final String RETRAIN     = "retrain";
    private static final String TAGS        = "tags";

    private static final String RETRAINING_SAMPLES_LABEL = "retrainingSamples";

    private JsonNodeAdapter jsonNodeAdapter;
    private LaughterInstanceRepository instanceRepository;
    private TagsRepository tagsRepository;
    private AudioVideoMappingRepository assetRepository;

    @Autowired
    public InstanceServiceImpl(
            JsonNodeAdapter jsonNodeAdapter,
            LaughterInstanceRepository instanceRepository,
            TagsRepository tagsRepository,
            AudioVideoMappingRepository assetRepository)
    {
        this.jsonNodeAdapter = jsonNodeAdapter;
        this.instanceRepository = instanceRepository;
        this.tagsRepository = tagsRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public String deleteInstance(@Nonnull Long id) {
        instanceRepository.delete(id);
        return "{\"message\":\"success\"}";
    }

    @Override
    public String updateInstance(Long id, JsonNode val) throws IOException {
        // Get the first and only instance with the given ID
        List<LaughterInstance> result = instanceRepository.findById(id);
        LaughterInstance update = result.get(0);

        // set the correctness flag
        if (val.get(ALG_CORRECT) != null) {
            update.setAlgCorrect(val.get(ALG_CORRECT).booleanValue());
        }
        // set the "use for retraining" flag
        if (val.get(RETRAIN) != null) {
            update.setUseForRetrain(val.get(RETRAIN).booleanValue());
        }

        // update tags
        if (val.get(TAGS) != null) {
            // remove any previous tags
            List<Tag> tags = tagsRepository.findAllPerInstance(id);
            for (Tag tag : tags) {
                tagsRepository.delete(tag.getId());
            }

            ObjectMapper mapper = new ObjectMapper();
            List<Tag> inputTags = Arrays.asList(
                    mapper.readValue(val.get(TAGS).toString(), Tag[].class)
            );
            // assign instance ID
            // TODO: NPE handling if unrecognized tag is provided.
            for (Tag tag : inputTags) {
                tag.setInstanceId(id);
            }
            // supply new list
            tagsRepository.save(inputTags);
        }

        instanceRepository.save(update);

        return "{}";
    }

    public String getTrainingEligibleInstances() {
        // value to be returned
        List<RetrainSampleUrlData> samples = new ArrayList<>();

        List<LaughterInstance> eligibleSamples = instanceRepository.getAllMarkedForRetraining();

        // for each instance, map them by s3_key
        Map<Long, List<LaughterInstance>> instancesPerVideo = new HashMap<>();
        for (LaughterInstance sample : eligibleSamples) {

            long videoKey = sample.getS3Key();
            instancesPerVideo.computeIfAbsent(videoKey, k -> new ArrayList<>());

            List<LaughterInstance> updatedList = instancesPerVideo.get(videoKey);
            updatedList.add(sample);
            instancesPerVideo.put(videoKey, updatedList);
        }

        // for each s3 key, grab video deets
        for (Map.Entry<Long, List<LaughterInstance>> entry : instancesPerVideo.entrySet()) {
            // assumes we find a single matching result
            List<AudioVideoMapping> vidResult = assetRepository.findById(entry.getKey().intValue());
            AudioVideoMapping video = vidResult.get(0);

            // for each instance, create result using instance and video deets
            for (LaughterInstance instance : entry.getValue()) {
                samples.add(
                    new RetrainSampleUrlData(
                        video.getBucket(),
                        video.getVideoFile(),
                        convertMsToSeconds(instance.getStartTime()),
                        convertMsToSeconds(instance.getStopTime()),
                        instance.getAlgCorrect()
                    )
                );
            }
        }

        return jsonNodeAdapter.createJsonArray(RETRAINING_SAMPLES_LABEL, samples);
    }

    private Double convertMsToSeconds(Long sec) {
        double result = (double) sec;
        return result / 1000;
    }
}
