package edu.uw.citw.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.service.metadata.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

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
    @PostMapping(
            value = "/instance/{instanceId}/participants/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String addParticipantToInstance(
            @Nonnull
            @PathVariable(value = "instanceId")
                    Integer instanceId,
            @Nullable
            @RequestBody String payload)
    throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode val = mapper.readTree(payload);
        return metadataService.postNewParticipant(instanceId, val);
    }

    @Nullable
    @ResponseBody
    @PostMapping(
            value = "/video/{videoId}/instances/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String postNewInstance(
            @Nonnull
            @PathVariable(value = "videoId")
                    Integer videoId,
            @Nullable
            @RequestBody
                    String payload)
    throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode val = mapper.readTree(payload);
        return metadataService.postNewInstance(videoId, val);
    }

    @Nullable
    @ResponseBody
    @DeleteMapping(
            value = "/participant/{id}/delete",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String deleteParticipant(
            @Nonnull
            @PathVariable(value = "id")
                    Integer id)
    throws IOException {

        return metadataService.deleteParticipant(id);
    }
}
