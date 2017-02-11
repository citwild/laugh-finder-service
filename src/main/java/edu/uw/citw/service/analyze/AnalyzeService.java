package edu.uw.citw.service.analyze;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.AudioVideoMapping;

import java.io.IOException;
import java.util.Optional;

/**
 * Returns found laughter instances in a video file or audio file.
 *
 * Created by milesdowe on 7/10/16.
 */
public interface AnalyzeService {

    JsonNode getLaughterInstancesFromAudio(AudioVideoMapping asset) throws IOException;

}
