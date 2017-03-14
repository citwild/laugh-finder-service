package edu.uw.citw.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.service.instance.InstanceService;
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

    @Nullable
    @ResponseBody
    @PostMapping(value = "/{id}/update")
    public String updateInstance(
            @PathVariable(value = "id")
            @Nonnull Long id,
            @RequestBody String payload)
    throws IOException {
        log.debug("Provided id value: {}, payload: {}", id, payload);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode val = mapper.readTree(payload);
        return instanceService.updateInstance(id, val);
    }
}
