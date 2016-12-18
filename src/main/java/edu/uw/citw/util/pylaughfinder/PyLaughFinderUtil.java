package edu.uw.citw.util.pylaughfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Handles running Python code.
 *
 * Created by Miles on 12/17/2016.
 */
@Component
public class PyLaughFinderUtil {

    private Logger log = LoggerFactory.getLogger(PyLaughFinderUtil.class);

    private static final String PHASE = "0";

    @Value("${testing.mainScript}")
    private String mainScript;
    @Value("${testDir}")
    private String testDir;

    public PyLaughFinderUtil() {}

    public Process runPythonLaughFinderScript(String bucket, String key) throws IOException {
        log.debug("Running Python script to search for laughter");

        String[] command = getCommand(bucket, key);

        try {
            Process proc = Runtime
                    .getRuntime()
                    .exec(command);

            // check exit code
            try {
                int exitValue = proc.waitFor();

                printPythonLaughFinderOutput(proc);

                if (exitValue != 0) {
                    throw new IOException("Python script exited with non-zero code; command used: " + command);
                }
            } catch (InterruptedException e) {
                log.error("There was a failure getting the exit code of the Python Script", e);
            }

            return proc;
        } catch (IOException e) {
            log.error("There was a failure analyzing the audio file: {}", key, e);
            throw e;
        }
    }

    public void printPythonLaughFinderOutput(Process proc) {
        try {
            // log any output
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = inputStream.readLine()) != null) {
                log.info("\t" + line);
            }

            // log any errors
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            while ((line = errorStream.readLine()) != null) {
                log.error("\t" + line);
            }
        } catch (IOException e) {
            log.error("There was a failure printing the Python script's output", e);
        }
    }

    @Nonnull
    public String[] getCommand(@Nonnull String bucket, @Nonnull String key) {
        log.info("Using values: \n\tmainScript: {}, \n\tbucket: {}, \n\tkey: {}, \n\tarff: {}, \n\tphase: {}",
                mainScript, bucket, key, testDir, PHASE);

        return new String[] {
                "python", mainScript,
                "--bucket", bucket,
                "--key", key,
                "--arff", testDir,
                "--phase", PHASE
        };
    }

    public String getMainScript() {
        return mainScript;
    }

    public void setMainScript(String mainScript) {
        this.mainScript = mainScript;
    }

    public String getTestDir() {
        return testDir;
    }

    public void setTestDir(String testDir) {
        this.testDir = testDir;
    }
}
