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
    @RequestMapping(
            value = "/metadata/instanceId/{instanceId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public JsonNode postMetadataPerInstance(
            @Nullable
            @RequestParam
                    String bucket,
            @Nullable
            @RequestParam
                    String key,
            @Nonnull
            @PathVariable(value = "instanceId")
                    Integer instanceId,
            @Nullable
            @RequestBody
                    String payload)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree("{\"bucket\":\" " + bucket + "\"," +
                    "\"key\": \""+ key+"\", \"instanceId\": " + instanceId + ", \"payload\": \"" + payload + "\"}");
        } catch (IOException e) {
            log.error("There was an error", e);
            return null;
        }
    }
}
