package edu.uw.citw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import edu.uw.citw.persistence.domain.LaughterType;
import edu.uw.citw.service.laughtype.LaughTypeService;
import edu.uw.citw.util.JsonNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

/**
 * For getting laugh types.
 *
 * Created by miles on 2/12/17.
 */
@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/types")
public class LaughTypeController {

    private static final Logger log = LoggerFactory.getLogger(LaughTypeController.class);

    private LaughTypeService laughTypeService;
    private JsonNodeAdapter jsonNodeAdapter;

    @Autowired
    public LaughTypeController(LaughTypeService laughTypeService, JsonNodeAdapter jsonNodeAdapter) {
        this.laughTypeService = laughTypeService;
        this.jsonNodeAdapter = jsonNodeAdapter;
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        ObjectMapper mapper = new ObjectMapper();
        List<LaughterType> types = laughTypeService.getAllLaughTypes();
        return mapper.<ArrayNode>valueToTree(types).toString();
    }

    @Nullable
    @ResponseBody
    @PostMapping(value = "/add", produces = MediaType.TEXT_PLAIN_VALUE)
    public String add(@RequestBody String requestJson) {
        try {
            laughTypeService.addType(requestJson);
        } catch (Exception e) {
            return "failure";
        }
        return "success";
    }
}
