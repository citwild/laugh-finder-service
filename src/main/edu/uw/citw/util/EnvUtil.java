package edu.uw.citw.util;

import org.springframework.stereotype.Service;

/**
 * For determining the system environment
 *
 * TODO: This is DEPRECATED as we will be using AWS ElasticTranscoder instead of FFMPEG
 *
 * Created by Miles on 6/26/2016.
 */
@Service
public class EnvUtil {

    public String getOS() {
        return System.getProperty("os.name");
    }

    public boolean isWindows() {
        return getOS().startsWith("Windows");
    }
}
