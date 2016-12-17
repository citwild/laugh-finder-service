package edu.uw.citw.service.analyze.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.test.TestingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * An implementation of the Analyze Service.
 * <p>
 * Created by milesdowe on 7/12/16.
 */
@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    private static final Logger log                   = LoggerFactory.getLogger(AnalyzeServiceImpl.class);
    private static final String FOUND_LAUGHTERS_LABEL = "foundLaughters";

    private TestingEngine   testEngine;
    private LaughterInstanceRepository timestampRepository;
    private JsonNodeAdapter jsonNodeAdapter;

    // External files relating to WEKA and the learning python script

    @Value("${testing.mainScript}")
    private String mainScript;
    @Value("${testing.arff.path}")
    private String arffLocation;
    @Value("${testDir}")
    private String testDir; // TODO: remove this and have it managed by DB

    @Autowired
    public AnalyzeServiceImpl(TestingEngine testEngine, JsonNodeAdapter jsonNodeAdapter) {
        this.testEngine = testEngine;
        this.jsonNodeAdapter = jsonNodeAdapter;
    }

    @Override
    public JsonNode getLaughterInstancesFromAudio(@Nonnull String bucket, @Nonnull String key) throws IOException {
        // FoundLaughter result = checkDatabaseForBucketAndKey(bucket, key)
        // if result is empty {
               FoundLaughter laughter = actionPerformed(bucket, key);
        // }
        return jsonNodeAdapter.createJsonObject(FOUND_LAUGHTERS_LABEL, laughter);
    }

//    public FoundLaughter checkDatabaseForBucketAndKey(@Nonnull String bucket, @Nonnull String key) {
//        FoundLaughter result = null;
//
//        LaughterInstanceRepository
//
//        return result;
//    }

    @Nonnull
    public FoundLaughter actionPerformed(@Nonnull String bucket, @Nonnull String key) throws IOException{
        log.debug("Beginning search for laughter in a given bucket/key");
        // prepare response; label is bucket and key
        FoundLaughter result = new FoundLaughter(bucket + "/" + key);

        runPythonLaughFinderScript(
                getCommand(bucket, key),
                key
        );

        log.debug("Getting laughter from arff file, returning result");
        testEngine.setArffPath(arffLocation);
        try {
            List<long[]> laughterList = testEngine.getLaughters();
            result = addLaughterInstances(laughterList, result);
            return result;
        } catch (Exception e) {
            log.error("There was an error searching for laughter in audio file: {}", key, e);
            return null;
        }
    }

    public Process runPythonLaughFinderScript(String[] command, String key) throws IOException {
        log.debug("Running Python script to search for laughter");
        try {
            Process proc = Runtime.getRuntime().exec(command);

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

    /**
     * Appends the provided laughters to the provided result
     */
    @Nonnull
    public FoundLaughter addLaughterInstances(@Nonnull List<long[]> laughterList, @Nonnull FoundLaughter result) {
        for (long[] laughter : laughterList) {
            long start = laughter[0];
            long stop  = laughter[1];
            result.addStartStop(start, stop);
        }
        return result;
    }

    @Nonnull
    public String[] getCommand(@Nonnull String bucket, @Nonnull String key) {
        String phase = "0";

        log.info("Using values: \n\tmainScript: {}, \n\tbucket: {}, \n\tkey: {}, \n\tarff: {}, \n\tphase: {}",
                mainScript, bucket, key, testDir, phase);

        return new String[] {
                "python", mainScript,
                "--bucket", bucket,
                "--key", key,
                "--arff", testDir,
                "--phase", phase
        };
    }

    public String getMainScript() {
        return mainScript;
    }

    public void setMainScript(String mainScript) {
        this.mainScript = mainScript;
    }

    public String getArffLocation() {
        return arffLocation;
    }

    public void setArffLocation(String arffLocation) {
        this.arffLocation = arffLocation;
    }

    public String getTestDir() {
        return testDir;
    }

    public void setTestDir(String testDir) {
        this.testDir = testDir;
    }
}
