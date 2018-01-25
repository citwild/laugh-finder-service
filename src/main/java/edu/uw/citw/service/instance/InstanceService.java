package edu.uw.citw.service.instance;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface InstanceService {

    String deleteInstance(Long id);

    String updateInstance(Long id, JsonNode val) throws IOException;
}
