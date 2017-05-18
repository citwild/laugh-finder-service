package edu.uw.citw.service.metadata.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.Participant;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.ParticipantType;
import edu.uw.citw.persistence.repository.InstanceParticipantsRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.TypesPerParticipantRepository;
import edu.uw.citw.service.metadata.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

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
    private TypesPerParticipantRepository typesPerParticipantRepository;

    @Autowired
    public MetadataServiceImpl(
            LaughterInstanceRepository laughterInstanceRepository,
            InstanceParticipantsRepository participantsRepository,
            TypesPerParticipantRepository typesPerParticipantRepository)
    {
        this.laughterInstanceRepository = laughterInstanceRepository;
        this.participantsRepository = participantsRepository;
        this.typesPerParticipantRepository = typesPerParticipantRepository;
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
                getStringValFromJsonNode(val, "speaker")
        );
        // true because entered by user
        submission.setAlgCorrect(true);

        LaughterInstance result = laughterInstanceRepository.save(submission);
        return result.toString();
    }

    @Override
    public String postNewParticipant(Integer instanceId, JsonNode val) {
        Participant participant = new Participant();

        // save participant
        participant.setInstanceId(instanceId.longValue());
        participant.setParticipantName(
                getStringValFromJsonNode(val, "name")
        );
        participant.setIntensity(val.get("intensity").asInt());
        Participant participantResult = participantsRepository.save(participant);

        // get result ID from participant row insert, use for tag inserts
        JsonNode tags = val.get("tags");
        for (Iterator<Map.Entry<String, JsonNode>> it = tags.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> field = it.next();

            // if type ID is true, add row
            if (field.getValue().asBoolean()) {
                typesPerParticipantRepository.save(
                        new ParticipantType(
                                Long.parseLong(field.getKey()),
                                participantResult.getId()
                        )
                );
            }
        }

        return participantResult.toString();
    }

    @Override
    public String deleteParticipant(Integer id) {
        participantsRepository.delete(id.longValue());
        return "Success";
    }


    private String getStringValFromJsonNode(JsonNode node, String field) {
        return (node.get(field).asText().equals("null")) ? null : node.get(field).asText();
    }
}
