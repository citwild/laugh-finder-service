package com.uwb.wfe.util.extract;

import com.uwb.wfe.util.EnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * To interface with the FFMPEG application
 *
 * Created by Miles on 6/26/2016.
 */
@Service
public class FfmpegAdapter {

    Logger log = LoggerFactory.getLogger(FfmpegAdapter.class);

    EnvUtil envUtil;

    @Value("${dependencies.ffmpeg.win.location}")
    private String winFfmpegCmd;
    @Value("${dependencies.ffmpeg.nix.location}")
    private String nixFfmpegCmd;

    @Autowired
    public FfmpegAdapter(EnvUtil envUtil) {
        this.envUtil = envUtil;
    }

    /**
     * Extracts audio from MP4 to WAV.
     */
    public void extractAudio(@NotNull String inputVideo, @NotNull String outputAudio)
            throws InterruptedException, IOException {
        log.info("Extracting audio from {} to {}", inputVideo, outputAudio);

        String cmdLocation = envUtil.isWindows() ? winFfmpegCmd : nixFfmpegCmd;

        // ffmpeg.exe -i input_video.mp4 output_audio.wav
        String[] cmd = {cmdLocation, "-i", inputVideo, outputAudio};
        Process proc = Runtime.getRuntime().exec(cmd);
        proc.waitFor();
        log.info("Finished extracting audio");
    }

    public String getWinFfmpegCmd() {
        return winFfmpegCmd;
    }

    public String getNixFfmpegCmd() {
        return nixFfmpegCmd;
    }
}
