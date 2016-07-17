package edu.uw.citw.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Simply uses the Jackson API to create JsonNode arrays/objects with just an object and a name.
 * Intended to limit verbosity of code.
 */
@Component("jsonNodeAdapter")
public class JsonNodeAdapter {

    public JsonNode createJsonArray(String nameOfArray, /*@Nullable*/ Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode result = (obj == null)
                ? mapper.valueToTree(new ArrayList<>())
                : mapper.valueToTree(obj);
        return mapper.createObjectNode().set(nameOfArray, result);
    }

    public JsonNode createJsonObject(String nameOfObject, /*@Nullable*/ Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.valueToTree(obj);
        return mapper.createObjectNode().set(nameOfObject, result);
    }
}
