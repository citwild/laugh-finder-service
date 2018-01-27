package edu.uw.citw.util.pylaughfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;

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
    @Value("${retrainScript}")
    private String retrainScript;
    @Value("${testDir}")
    private String testDir;

    public PyLaughFinderUtil() {}


    @Nonnull
    public Process runPythonLaughFinderScript(
            @Nonnull String bucket,
            @Nonnull String key)
    throws IOException
    {
        log.debug("Running Python script to search for laughter");

        String[] command = getTestingCommand(bucket, key);

        try {
            Process proc = Runtime.getRuntime().exec(command);
            runProcess(proc, command);

            return proc;
        } catch (IOException e) {
            log.error("There was a failure analyzing the audio file: {}", key, e);
            throw e;
        }
    }


    public String runReTrainingScript(@Nonnull String jsonSamples) throws IOException {
        StringBuilder result = new StringBuilder();
        String[] command = getTrainingCommand(jsonSamples);

        try {
            Process proc = Runtime.getRuntime().exec(command);
            runProcess(proc, command);

            // get output
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = inputStream.readLine()) != null) {
                result.append(line + "\n");
                log.info("\t" + line);
            }

            return result.toString();
        } catch (IOException e) {
            log.error("There was a failure training a new model: ", e);
            throw e;
        }
    }


    @Nonnull
    public String[] getTestingCommand(@Nonnull String bucket, @Nonnull String key) {
        log.info("Using values: \n\tmainScript: {}, \n\tbucket: {}, \n\tkey: {}, \n\tarff: {}, \n\tphase: {}",
                mainScript, bucket, key, testDir, PHASE);

        return new String[] {
                "python3", mainScript,
                "--bucket", bucket,
                "--key", key,
                "--arff", testDir,
                "--phase", PHASE
        };
    }


    /**
     * Handles running the python code for re-training. Needs a JSON of the videos to use and
     *   their timestamps. See main.py of the training code to see.
     */
    @Nonnull
    public String[] getTrainingCommand(@Nonnull String jsonSamples) {
        log.info("Using values: \n\tmainScript: {}, \n\tsamples: {}", retrainScript, jsonSamples);

        return new String[] {
                "python3", retrainScript,
                "--samples", jsonSamples
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

    // for logging System.exec command
    private String printCommandArray(String[] cmd) {
        StringJoiner val = new StringJoiner(",");
        for (String str : cmd) {
            val.add(str);
        }
        return val.toString();
    }

    private void runProcess(Process proc, String[] command) throws IOException {
        try {
            int exitValue = proc.waitFor();

            // log any errors
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            if (exitValue != 0) {
                // Log process error output
                String line;
                while ((line = errorStream.readLine()) != null) {
                    log.error("\t" + line);
                }

                throw new IOException("Python script exited with non-zero code; command used: "
                        + printCommandArray(command));
            }
        } catch (InterruptedException e) {
            log.error("There was a failure getting the exit code of the Python Script", e);
        }
    }
}
