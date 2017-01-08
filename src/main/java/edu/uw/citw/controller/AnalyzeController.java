package edu.uw.citw.controller;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.mapping.AudioVideoMappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

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
    private AudioVideoMappingUtil audioVideoMappingUtil;

    @Autowired
    public AnalyzeController(AnalyzeService analyzeService, AudioVideoMappingUtil audioVideoMappingUtil) {
        this.analyzeService = analyzeService;
        this.audioVideoMappingUtil = audioVideoMappingUtil;
    }

    /**
     * Returns instances of laughter in the specified bucket/key combination.
     *
     * Key expected to look like the following:
     *     <code>Compressed/2014-01-31/Huddle/00079-320.mp4</code>
     */
    @Nonnull
    @ResponseBody
    @RequestMapping(value = "/video",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode analyzeVideo(
            @Nonnull @RequestParam String bucket,
            @Nonnull @RequestParam String key
    ) throws IOException {
        log.info("Analyzing laughter in S3 bucket {}, key {}", bucket, key);
        Optional<AudioVideoMapping> asset = audioVideoMappingUtil.getAudioExtractOfVideo(bucket, key);

        if (asset.isPresent()) {
            // [0] == bucket string, [1] == audio filename string
//            return analyzeService.getLaughterInstancesFromAudio(bucketAndKey[0], bucketAndKey[1]);
            return analyzeService.getLaughterInstancesFromAudio(asset.get());
        } else {
            throw new IOException("There was an error...");
        }
    }


    /**
     * Returns instances of laughter in the specified bucket/key combination.
     *
     * Key expected to look like the following:
     *     <code>ExtractedAudio/Compressed/2014-01-31/Huddle/00079-320.wav</code>
     *
     * TODO: fix this because it doesn't work
     */
    @Deprecated
    @Nonnull
    @ResponseBody
    @RequestMapping(value = "/audio",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode analyzeAudio(
            @Nonnull @RequestParam String bucket,
            @Nonnull @RequestParam String key
    ) throws IOException {
        log.info("Analyzing laughter in S3 bucket {}, key {}", bucket, key);
//        return analyzeService.getLaughterInstancesFromAudio(bucket, key);
        return null;
    }
}
