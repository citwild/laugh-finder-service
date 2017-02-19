package edu.uw.citw.service.laughtype.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.uw.citw.persistence.domain.LaughterType;
import edu.uw.citw.persistence.repository.LaughTypesRepository;
import edu.uw.citw.service.laughtype.LaughTypeService;
import edu.uw.citw.util.JsonNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * For getting laugh types.
 *
 * Created by miles on 2/12/17.
 */
@Service
public class LaughTypeServiceImpl implements LaughTypeService {

    private static final Logger log = LoggerFactory.getLogger(LaughTypeServiceImpl.class);
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "type";
    private static final String DESC_FIELD = "description";
    private static final String CONSIDERED_FIELD = "considered";

    private LaughTypesRepository laughTypesRepository;
    private JsonNodeAdapter jsonNodeAdapter;

    @Autowired
    public LaughTypeServiceImpl(LaughTypesRepository laughTypesRepository, JsonNodeAdapter jsonNodeAdapter) {
        this.laughTypesRepository = laughTypesRepository;
        this.jsonNodeAdapter = jsonNodeAdapter;
    }

    @Override
    public List<LaughterType> getAllLaughTypes() {
        List<LaughterType> types = laughTypesRepository.findAll();
        types.forEach(type -> log.debug(type.toString()));
        return types;
    }

    @Override
    public void addType(String requestJson) throws IOException {
        // parse input JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(requestJson);

        LaughterType result = new LaughterType();
        result.setType(json.get(NAME_FIELD).asText());
        result.setDescription(json.get(DESC_FIELD).asText());
        result.setConsidered(true);

        // submit to DB
        laughTypesRepository.save(result);
    }

    @Override
    public void addTypeArray(String requestJson) throws IOException {
        // parse input JSON
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode json = (ArrayNode) mapper.readTree(requestJson);

        for (JsonNode obj : json) {
            Long id = obj.get(ID_FIELD).asLong();
            String type = obj.get(NAME_FIELD).asText();
            String desc = obj.get(DESC_FIELD).asText();
            Boolean considered = obj.get(CONSIDERED_FIELD).asBoolean();

            LaughterType updatedVal = new LaughterType(id, type, (desc == null) ? "" : desc, considered);

            log.info("Updating laugh type: {}", updatedVal);

            // submit to DB
            laughTypesRepository.save(updatedVal);
        }
    }
}
