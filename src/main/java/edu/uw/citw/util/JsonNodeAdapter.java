package edu.uw.citw.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simply uses the Jackson API to create JsonNode arrays/objects with just an object and a name.
 * Intended to limit verbosity of code.
 */
@Component
public class JsonNodeAdapter {

    private static final Logger log = LoggerFactory.getLogger(JsonNodeAdapter.class);

    public String createJsonArray(String nameOfArray, @Nullable Object input) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode result = mapper.createArrayNode();
        List<Object> list = (List) input;
        for (Object object : list) {
            result.add(mapper.valueToTree(object));
        }
        return mapper.createObjectNode().set(nameOfArray, result).toString();
    }

    public String createJsonObject(String nameOfObject, @Nullable Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.valueToTree(obj);
        return mapper.createObjectNode().set(nameOfObject, result).toString();
    }
}
