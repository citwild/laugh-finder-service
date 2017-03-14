package edu.uw.citw.controller;

import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.mapping.AudioVideoMappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;

/**
 * For finding laughter instances in audio streams.
 * <p>
 * Created by Miles on 6/26/2016.
 */
// TODO: 1/30/2017 remove localhost when done testing
@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/analyze")
public class AnalyzeController {

    private static final Logger log = LoggerFactory.getLogger(AnalyzeController.class);

    private AnalyzeService analyzeService;
    private AudioVideoMappingUtil audioVideoMappingUtil;

    @Autowired
    public AnalyzeController(
            AnalyzeService analyzeService,
            AudioVideoMappingUtil audioVideoMappingUtil)
    {
        this.analyzeService = analyzeService;
        this.audioVideoMappingUtil = audioVideoMappingUtil;
    }

    /**
     * Returns instances of laughter in the specified bucket/key combination.
     *
     * Key expected to look like the following: <code>Compressed/2014-01-31/Huddle/00079-320.mp4</code>
     */
    @Nullable
    @ResponseBody
    @GetMapping(value = "/video", produces = MediaType.APPLICATION_JSON_VALUE)
    public String analyzeVideo(@Nullable @RequestParam String bucket, @Nullable @RequestParam String key)
            throws IOException {
        log.info("Analyzing laughter in S3 bucket {}, key {}", bucket, key);
        if (bucket != null && key != null) {
            Optional<AudioVideoMapping> asset = audioVideoMappingUtil.getAudioExtractOfVideo(bucket, key);

            if (asset.isPresent())
                return analyzeService.getLaughterInstancesFromAudio(asset.get());
            else
                throw new IOException("Laughter instances turned up empty");
        }
        return null; // TODO: provide a helpful error message
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/video", produces = MediaType.APPLICATION_JSON_VALUE)
    public String analyzeVideo(@Nullable @RequestParam Integer id) throws IOException {

        log.info("Analyzing laughter in video id {}", id);
        if (id != null) {
            Optional<AudioVideoMapping> asset = audioVideoMappingUtil.getAudioExtractOfVideo(id);

            if (asset.isPresent())
                return analyzeService.getLaughterInstancesFromAudio(asset.get());
            else
                throw new IOException("Laughter instances turned up empty");
        }
        throw new IllegalArgumentException("Provided null argument");
    }
}
