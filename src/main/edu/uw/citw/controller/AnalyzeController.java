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
 * For finding laughter instances in audio streams.
 * <p>
 * Created by Miles on 6/26/2016.
 */
@RestController
@RequestMapping(value = "/analyze")
public class AnalyzeController {

    private static final Logger log = LoggerFactory.getLogger(AnalyzeController.class);

    private AnalyzeService analyzeService;
    private ObjectMapper   mapper;

    @Autowired
    public AnalyzeController(AnalyzeService analyzeService, ObjectMapper mapper) {
        this.analyzeService = analyzeService;
        this.mapper = mapper;
    }

    /**
     * Returns instances of laughter in the specified bucket/key combination.
     *
     * Key expected to look like the following:
     *     <code>ExtractedAudio/Compressed/2014-01-31/Huddle/00079-320.wav</code>
     */
    @RequestMapping(value = "/video",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @NotNull
    public JsonNode analyzeVideo(
            @NotNull @RequestParam String bucket,
            @NotNull @RequestParam String key
    ) throws IOException {
        // extract audio from video, then use audio's path
        log.info("Analyzing laughter in S3 bucket {}, key {}", bucket, key);
        return analyzeService.getLaughterInstancesFromAudio(bucket, key);
    }
}
