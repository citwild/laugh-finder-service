package edu.uw.citw.service.metadata.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.repository.InstanceParticipantsRepository;
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
    private InstanceParticipantsRepository participantsRepository;
    @Autowired
    public MetadataServiceImpl(
            LaughterInstanceRepository laughterInstanceRepository,
            InstanceParticipantsRepository participantsRepository)
    {
        this.laughterInstanceRepository = laughterInstanceRepository;
        this.participantsRepository = participantsRepository;
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
        submission.setStartTime(
                convertSecondsToMs(val.get("start").asDouble())
        );
        submission.setStopTime(
                convertSecondsToMs(val.get("stop").asDouble())
        );

        submission.setJoke(val.get("joke").asBoolean());
        submission.setJokeSpeaker(
                getStringValFromJsonNode(val, "speaker")
        );
        // following is true because entered by user
        submission.setAlgCorrect(true);
        submission.setUserMade(true);

        LaughterInstance result = laughterInstanceRepository.save(submission);
        return result.toString();
    }


    private String getStringValFromJsonNode(JsonNode node, String field) {
        return (node.get(field).asText().equals("null")) ? null : node.get(field).asText();
    }

    protected Long convertSecondsToMs(Double sec) {
        return Double.valueOf(sec * 1000).longValue();
    }
}
