package edu.uw.citw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.uw.citw.service.extract.ExtractService;
import edu.uw.citw.service.extract.impl.ExtractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * For API calls that extract audio from requested audio paths
 *
 * Created by Miles on 6/26/2016.
 */
@RestController
@RequestMapping(value = "/extract")
public class ExtractController {

    private static final Logger log = LoggerFactory.getLogger(ExtractController.class);

    private static final String VIDEO_FILE_TYPE = ".mp4";
    private static final String AUDIO_FILE_TYPE = ".wav";

    @Value("${assets.video.inputPath}")
    private String inputPath;
    @Value("${assets.audio.outputPath}")
    private String outputPath;

    private ExtractService extractService;
    private ObjectMapper   mapper;

    @Autowired
    public ExtractController(ExtractServiceImpl extractService, ObjectMapper mapper) {
        this.extractService = extractService;
        this.mapper = mapper;
    }

    @RequestMapping(value    = "/video/id/{vidId}",
                    method   = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @NotNull
    public JsonNode extractAudioFromVideo(
            @PathVariable @NotNull String vidId
    ) throws JsonProcessingException, IOException {
        String vid = inputPath + vidId + VIDEO_FILE_TYPE;
        String result = outputPath + "result" + AUDIO_FILE_TYPE;
        try {
            extractService.extractAudio(vid, result);
        } catch (IOException | InterruptedException e) {
            log.error("Failed to extract audio", e);
            return mapper.readValue("{\"status\":\"failure\", \"message\":\"" + e + "\"}", ObjectNode.class);
        }
        return mapper.readValue("{\"status\":\"success\"}", ObjectNode.class);
    }
}
