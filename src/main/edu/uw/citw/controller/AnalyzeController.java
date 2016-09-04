package edu.uw.citw.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uw.citw.service.analyze.AnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * For simply checking audio streams for laughter instances; not intended to refine model.
 * <p>
 * Created by Miles on 6/26/2016.
 */
@RestController
@RequestMapping(value = "/analyze")
public class AnalyzeController {

    private static final Logger log = LoggerFactory.getLogger(AnalyzeController.class);

    private static final String AUDIO_FILE_TYPE = ".wav";

    @Value("assets.audio.outputPath")
    private String audioPath;

    private AnalyzeService analyzeService;
    private ObjectMapper   mapper;

    @Autowired
    public AnalyzeController(AnalyzeService analyzeService, ObjectMapper mapper) {
        this.analyzeService = analyzeService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/video/id/{vidId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @NotNull
    public JsonNode analyzeVideo(
            @PathVariable @NotNull String bucket,
            @PathVariable @NotNull String key
    ) throws IOException {
        // extract audio from video, then use audio's path
        return analyzeService.getLaughterInstancesFromAudio(bucket, key);
    }
}
