package com.uwb.wfe.util.extract;

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

    @Value("${dependencies.ffmpeg.location}")
    String ffmpegCmd;

    public void executeFfmpeg() throws InterruptedException, IOException {
        String[] cmd = {ffmpegCmd};
        Process proc = Runtime.getRuntime().exec(cmd);
        proc.waitFor();
    }
}
