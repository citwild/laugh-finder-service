package com.uwb.wfe.util;

import org.springframework.stereotype.Service;

/**
 * For determining the system environment
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
