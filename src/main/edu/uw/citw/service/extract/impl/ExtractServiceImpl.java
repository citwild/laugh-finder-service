package edu.uw.citw.service.extract.impl;

import edu.uw.citw.service.extract.ExtractService;
import edu.uw.citw.util.EnvUtil;
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
public class ExtractServiceImpl implements ExtractService {

    private Logger log = LoggerFactory.getLogger(ExtractServiceImpl.class);

    private EnvUtil envUtil;

    @Value("${extract.ffmpeg.win.location}")
    private String winFfmpegCmd;
    @Value("${extract.ffmpeg.nix.location}")
    private String nixFfmpegCmd;

    @Autowired
    public ExtractServiceImpl(EnvUtil envUtil) {
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

        if (proc.exitValue() != 0)
            log.warn("There was a failure running FFMPEG");
        else
            log.info("Finished extracting audio");
    }

    public String getWinFfmpegCmd() {
        return winFfmpegCmd;
    }

    public String getNixFfmpegCmd() {
        return nixFfmpegCmd;
    }
}
