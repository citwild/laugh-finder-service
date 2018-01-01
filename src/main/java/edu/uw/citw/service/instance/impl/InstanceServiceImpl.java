package edu.uw.citw.service.instance.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.Tag;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.TagsRepository;
import edu.uw.citw.service.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    private LaughterInstanceRepository instanceRepository;
    private TagsRepository tagsRepository;

    @Autowired
    public InstanceServiceImpl(LaughterInstanceRepository instanceRepository, TagsRepository tagsRepository) {
        this.instanceRepository = instanceRepository;
        this.tagsRepository = tagsRepository;
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
}
