package com.uwb.wfe.service.extract;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Handles work extracting audio from video
 *
 * Created by milesdowe on 6/28/16.
 */
public interface ExtractService {

    void extractAudio(@NotNull String inputPath, @NotNull String outputPath)
            throws InterruptedException, IOException;
}
