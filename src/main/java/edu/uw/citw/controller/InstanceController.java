package edu.uw.citw.controller;

import edu.uw.citw.service.instance.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * For viewing and modifying instances.
 *
 * Created by miles on 3/5/17.
 */
@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/instance")
public class InstanceController {

    private static final Logger log = LoggerFactory.getLogger(InstanceController.class);

    private InstanceService instanceService;

    @Autowired
    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @Nullable
    @ResponseBody
    @DeleteMapping(value = "/{id}/delete")
    public String deleteInstance(
            @PathVariable(value = "id")
            @Nonnull Long id)
    {
        log.debug("Provided id value: {}", id);
        return instanceService.deleteInstance(id);
    }
}
