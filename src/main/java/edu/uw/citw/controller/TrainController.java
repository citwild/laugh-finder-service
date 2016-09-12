package edu.uw.citw.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.uw.citw.service.train.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Train the model with supervised data.:w
 *
 * Created by Miles on 6/26/2016.
 */
@RestController
@RequestMapping(value = "/train")
public class TrainController {

    private static final Logger log = LoggerFactory.getLogger(TrainController.class);

    private TrainService trainService;
    private ObjectMapper mapper;

    @Autowired
    public TrainController(TrainService trainService, ObjectMapper mapper) {
        this.trainService = trainService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/model",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @NotNull
    public JsonNode trainModel() throws InterruptedException, IOException {
        trainService.trainModel();
        return mapper.readValue("{\"status\":\"success\"}", ObjectNode.class);
    }
}
