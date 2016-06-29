package com.uwb.wfe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwb.wfe.service.extract.ExtractService;
import com.uwb.wfe.service.extract.impl.ExtractServiceImpl;
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

    @Value("${assets.video.inputPath}")
    private String inputPath;
    @Value("${assets.audio.outputPath}")
    private String outputPath;

    private ExtractService extractService;
    private ObjectMapper mapper;

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
    public String extractAudioFromVideo(
            @PathVariable @NotNull String vidId
    ) throws JsonProcessingException {
        String vid = inputPath + vidId + VIDEO_FILE_TYPE;
        try {
            extractService.extractAudio(vid, outputPath);
        } catch (IOException | InterruptedException e) {
            log.error("Failed to extract audio", e);
        }
        return mapper.writeValueAsString("{\"status\":\"success\"}");
    }
}
