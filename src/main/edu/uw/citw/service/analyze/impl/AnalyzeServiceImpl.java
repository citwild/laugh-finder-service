package edu.uw.citw.service.analyze.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.model.FoundLaughters;
import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.test.TestEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * An implementation of the Analyze Service.
 * <p>
 * Created by milesdowe on 7/12/16.
 */
@Service("analyzeService")
public class AnalyzeServiceImpl implements AnalyzeService {

    private static final Logger log                   = LoggerFactory.getLogger(AnalyzeServiceImpl.class);
    private static final String FOUND_LAUGHTERS_LABEL = "foundLaughters";

    private TestEngine      testEngine;
    private JsonNodeAdapter jsonNodeAdapter;

    @Value("${testing.arff.path}")
    private String arffLocation;
    @Value("${testing.program.path}")
    private String testingScriptLocation;
    @Value("${testDir}")
    private String testDir;

    @Autowired
    public AnalyzeServiceImpl(TestEngine testEngine, JsonNodeAdapter jsonNodeAdapter) {
        this.testEngine = testEngine;
        this.jsonNodeAdapter = jsonNodeAdapter;
    }

    @Override
    public JsonNode getLaughterInstancesFromAudio(@NotNull String audioId) {
        FoundLaughters laughters = actionPerformed(audioId);
        return jsonNodeAdapter.createJsonObject(FOUND_LAUGHTERS_LABEL, laughters);
    }

    @NotNull
    public FoundLaughters actionPerformed(@NotNull String audioId) {
        FoundLaughters result = new FoundLaughters(audioId);

        String command = "/usr/local/bin/python3 "
                + testingScriptLocation
                + " --audio " + audioId
                + " --arff " + testDir
                + " --phase 0";
        Runtime runTime = Runtime.getRuntime();
        try {
            Process proc = runTime.exec(command);
            // retrieve output from Python script
            BufferedReader bfr = new BufferedReader(
                    new InputStreamReader(
                            proc.getInputStream()
                    )
            );
            String line;
            while ((line = bfr.readLine()) != null) {
                // display each output line from Python script
                log.debug(line);
            }
        } catch (IOException e) {
            log.error("Failure doing something", e);
        }

        testEngine.setArffPath(arffLocation);
        List<long[]> laughterList = testEngine.getLaughters();

        result = addLaughterInstances(laughterList, result);

        return result;
    }

    @NotNull
    private FoundLaughters addLaughterInstances(@NotNull List<long[]> laughterList, @NotNull FoundLaughters result) {
        for (long[] laughter : laughterList) {
            long start = laughter[0];
            long stop  = laughter[1];
            result.addStartStop(start, stop);
        }
        return result;
    }
}
