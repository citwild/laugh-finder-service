package edu.uw.citw.service.metadata.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.model.LaughInstance;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.service.metadata.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * For adding and getting metadata related to specific videos/audio.
 *
 * Created by miles on 2/11/17.
 */
@Service
public class MetadataServiceImpl implements MetadataService {

    private static final Logger log = LoggerFactory.getLogger(MetadataServiceImpl.class);

    private LaughterInstanceRepository laughterInstanceRepository;

    @Autowired
    public MetadataServiceImpl(LaughterInstanceRepository laughterInstanceRepository) {
        this.laughterInstanceRepository = laughterInstanceRepository;
    }

    @Override
    public JsonNode postMetadataPerInstance() {
        return null;
    }

    public String postNewInstance(Integer videoId, JsonNode val) {
        LaughterInstance submission = new LaughterInstance();

        // set values
        submission.setS3Key(videoId.longValue());
        // times 1000 because DB saves values as milliseconds, not seconds
        submission.setStartTime(val.get("start").asLong() * 1000);
        submission.setStopTime(val.get("stop").asLong() * 1000);
        submission.setJoke(val.get("joke").asBoolean());
        submission.setJokeSpeaker(
                (val.get("speaker").asText().equals("null")) ? null : val.get("speaker").asText()
        );
        // true because entered by user
        submission.setAlgCorrect(true);

        laughterInstanceRepository.save(submission);
        return "test";
    }
}
