package edu.uw.citw.controller;

import edu.uw.citw.model.LaughInstance;
import edu.uw.citw.service.metadata.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * For updating laughter instances with additional data.
 *
 * Created by miles on 2/11/17.
 */
@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/metadata")
public class MetadataController {

    private static final Logger log = LoggerFactory.getLogger(MetadataController.class);

    private MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @Nullable
    @ResponseBody
    @PutMapping(
            value = "/put/instanceId/{instanceId}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String postMetadataPerInstance(
            @Nonnull
            @PathVariable(value = "instanceId")
                    Integer instanceId,
            @Nullable
            @RequestBody
                    LaughInstance payload)
    {
        return "instanceId=" + instanceId + ", payload=" + payload;
    }
}
