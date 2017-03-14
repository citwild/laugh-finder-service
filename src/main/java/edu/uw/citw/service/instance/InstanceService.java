package edu.uw.citw.service.instance;

import com.fasterxml.jackson.databind.JsonNode;

public interface InstanceService {

    String deleteInstance(Long id);

    String updateInstance(Long id, JsonNode val);
}
