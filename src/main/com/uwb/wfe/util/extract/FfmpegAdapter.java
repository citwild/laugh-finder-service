package com.uwb.wfe.util.extract;

import com.uwb.wfe.util.EnvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * To interface with the FFMPEG application
 *
 * Created by Miles on 6/26/2016.
 */
@Service
public class FfmpegAdapter {

    EnvUtil envUtil;

    @Value("${dependencies.ffmpeg.win.location}")
    String winFfmpegCmd;
    @Value("${dependencies.ffmpeg.nix.location}")
    String nixFfmpegCmd;

    @Autowired
    public FfmpegAdapter(EnvUtil envUtil) {
        this.envUtil = envUtil;
    }

    /**
     * Extracts audio from MP4 to WAV.
     */
    public void extractAudio(String targetVideo, String outputAudio)
            throws InterruptedException, IOException {
        String cmdLocation = envUtil.isWindows() ? winFfmpegCmd : nixFfmpegCmd;

        String[] cmd = {cmdLocation + " -i", targetVideo, outputAudio};
        Process proc = Runtime.getRuntime().exec(cmd);
        proc.waitFor();
    }

}
