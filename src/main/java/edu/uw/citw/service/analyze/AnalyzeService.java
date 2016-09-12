package edu.uw.citw.service.analyze;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Returns found laughter instances in a video file or audio file.
 *
 * Created by milesdowe on 7/10/16.
 */
public interface AnalyzeService {

    JsonNode getLaughterInstancesFromAudio(String bucket, String audioId);
}
