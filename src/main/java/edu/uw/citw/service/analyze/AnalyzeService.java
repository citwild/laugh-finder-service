package edu.uw.citw.service.analyze;

import edu.uw.citw.persistence.domain.AudioVideoMapping;

import java.io.IOException;

/**
 * Returns found laughter instances in a video file or audio file.
 *
 * Created by milesdowe on 7/10/16.
 */
public interface AnalyzeService {

    String getLaughterInstancesFromAudio(AudioVideoMapping asset) throws IOException;

}
