package edu.uw.citw.service.analyze.impl;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.model.StartStop;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.test.TestingEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeServiceImplTest {

    private TestingEngine testingEngine;
    private JsonNodeAdapter jsonNodeAdapter;

    private AnalyzeServiceImpl unitUnderTest;

    @Before
    public void setUp() throws Exception {
        testingEngine = mock(TestingEngine.class);
        jsonNodeAdapter = mock(JsonNodeAdapter.class);

        unitUnderTest = new AnalyzeServiceImpl(testingEngine, jsonNodeAdapter);

        // set values only initialized by Spring context
        unitUnderTest.setArffLocation("testArff.arff");
        unitUnderTest.setMainScript("testScript.py");
        unitUnderTest.setTestDir("testDir");
    }

    @Test
    public void getCommand_ShouldProvideExpectedCommand() throws Exception {
        String[] result = unitUnderTest.getCommand("testBucket", "testKey");

        assertEquals("testScript.py", result[1]);       // script location
        assertEquals("testBucket", result[3]); // bucket
        assertEquals("testKey", result[5]);    // key
        assertEquals("testDir", result[7]);       // testDir
        assertEquals("0", result[9]);          // phase
    }

    @Test
    public void addLaughterInstances_ShouldAddStartStops() throws Exception {
        List<long[]> input = Arrays.asList(new long[] {111, 222}, new long[]{333, 444});
        FoundLaughter result = new FoundLaughter("testFile");

        unitUnderTest.addLaughterInstances(input, result);

        assertEquals(2, result.getTimestamps().size());

        StartStop first = result.getTimestamps().get(0);
        assertEquals(111, first.getStart());
        assertEquals(222, first.getStop());

        StartStop second = result.getTimestamps().get(1);
        assertEquals(333, second.getStart());
        assertEquals(444, second.getStop());
    }
}