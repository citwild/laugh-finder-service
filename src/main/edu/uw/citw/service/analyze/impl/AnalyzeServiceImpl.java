package edu.uw.citw.service.analyze.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.test.TestEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * An implementation of the Analyze Service.
 *
 * Created by milesdowe on 7/12/16.
 */
public class AnalyzeServiceImpl implements AnalyzeService {

    private static final Logger log = LoggerFactory.getLogger(AnalyzeServiceImpl.class);

    @Value("${testing.arff.path}")
    private String arffLocation;

    @Value("${testing.program.path}")
    private String testingScriptLocation;

    @Value("${testDir}")
    private String testDir;

    @Override
    public JsonNode getLaughterInstancesFromVideo(String vidId) {
        // pull audio from video
        // analyze instances
        // add to model
        return null;
    }

    @Override
    public JsonNode getLaughterInstancesFromAudio(String audioId) {
        return null;
    }

    public void actionPerformed(String audioId) {
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
                log.info(line);
            }
        } catch (IOException e) {
            log.error("Failure doing something", e);
        }

        TestEngine    engine               = new TestEngine(arffLocation);
        List<long[]>  laughtersInMilliSecs = engine.getLaughters();

        for (int i = 0; i < laughtersInMilliSecs.size(); i++) {
            long start = laughtersInMilliSecs.get(i)[0];
            long end = laughtersInMilliSecs.get(i)[1];
        }
    }
}
