package edu.uw.citw.service.metadata;

import com.fasterxml.jackson.databind.JsonNode;

public interface MetadataService {
    JsonNode postMetadataPerInstance();

    String postNewInstance(Integer videoId, JsonNode val);

    String postNewParticipant(Integer instanceId, JsonNode val);

    String deleteParticipant(Integer id);
}
