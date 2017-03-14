package edu.uw.citw.service.instance.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.service.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * For getting and modifying instances.
 *
 * Created by miles on 3/5/17.
 */
@Service
public class InstanceServiceImpl implements InstanceService {

    private LaughterInstanceRepository instanceRepository;

    @Autowired
    public InstanceServiceImpl(LaughterInstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @Override
    public String deleteInstance(@Nonnull Long id) {
        instanceRepository.delete(id);
        return "{\"message\":\"success\"}";
    }

    @Override
    public String updateInstance(Long id, JsonNode val) {
        List<LaughterInstance> result = instanceRepository.findById(id.intValue());
        LaughterInstance update = result.get(0);
        if (val.get("start") != null) {
            update.setStartTime(val.get("start").longValue());
        }
        if (val.get("stop") != null) {
            update.setStopTime(val.get("stop").longValue());
        }
        if (val.get("joke") != null) {
            update.setJoke(val.get("joke").booleanValue());
        }
        if (val.get("speaker") != null) {
            update.setJokeSpeaker(val.get("speaker").textValue());
        }
        if (val.get("algCorrect") != null) {
            update.setAlgCorrect(val.get("algCorrect").booleanValue());
        }

        instanceRepository.save(update);

        return "{}";
    }
}
