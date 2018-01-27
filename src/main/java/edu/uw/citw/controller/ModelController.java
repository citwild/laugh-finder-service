package edu.uw.citw.controller;

import edu.uw.citw.service.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

/**
 * For viewing and modifying instances.
 *
 * Created by miles on 3/5/17.
 */
@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/model")
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    private ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/get/all")
    public String getAllModels() {
        log.debug("Retrieving all new models");
        return modelService.getAllModels();
    }

    @Nullable
    @ResponseBody
    @PostMapping(value = "/changeto/{id}")
    public String switchModel(
            @PathVariable(value = "id")
            @Nonnull Long id)
    {
        log.debug("Changing laughter model to ID {}", id);

        return modelService.switchModel(id);
    }

    @Nullable
    @ResponseBody
    @PostMapping(value = "/retrain")
    public void retrainNewModel() throws IOException {
        log.debug("Retraining a new model");
        modelService.retrainNewModel();
    }
}
