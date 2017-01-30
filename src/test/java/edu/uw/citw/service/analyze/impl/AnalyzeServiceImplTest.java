package edu.uw.citw.service.analyze.impl;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.model.StartStop;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.persistence.InstancePersistenceUtil;
import edu.uw.citw.util.pylaughfinder.PyLaughFinderUtil;
import edu.uw.citw.util.test.TestingEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeServiceImplTest {

    private TestingEngine testingEngine;
    private InstancePersistenceUtil instancePersistenceUtil;
    private PyLaughFinderUtil pyLaughFinderUtil;
    private JsonNodeAdapter jsonNodeAdapter;

    private AnalyzeServiceImpl unitUnderTest;

    @Before
    public void setUp()
    throws Exception
    {
        testingEngine = mock(TestingEngine.class);
        instancePersistenceUtil = mock(InstancePersistenceUtil.class);
        pyLaughFinderUtil = mock(PyLaughFinderUtil.class);
        jsonNodeAdapter = mock(JsonNodeAdapter.class);

        unitUnderTest = new AnalyzeServiceImpl(testingEngine, instancePersistenceUtil, pyLaughFinderUtil, jsonNodeAdapter);

        // set values only initialized by Spring context
        unitUnderTest.setArffLocation("testArff.arff");
    }

    /**
     * Run python code if laugh instances don't exist
     */
    @Test
    public void getLaughterInstancesFromAudio_shouldRunPythonCode_ifDatabaseFindsNothing()
    throws Exception
    {
        // pretend database didn't find anything
        when(instancePersistenceUtil.getInstancesByBucketAndKey(anyString(), anyString()))
                .thenReturn(Optional.empty());
        // pretend python code is run
        when(pyLaughFinderUtil.runPythonLaughFinderScript(anyString(), anyString()))
                .thenReturn(null);
        when(testingEngine.getLaughters())
                .thenReturn(getStubInstances());

        // run test
        unitUnderTest.getLaughterInstancesFromAudio(getStubAudioVideoMapping());

        verify(pyLaughFinderUtil, times(1))
                .runPythonLaughFinderScript(anyString(), anyString());
    }

    /**
     * Don't run python code if laugh instances exist
     */
    @Test
    public void getLaughterInstancesFromAudio_shouldNotRunPythonCode_ifDatabaseFindsSomething()
    throws Exception
    {
        // pretend database found something
        when(instancePersistenceUtil.getInstancesByBucketAndKey(anyString(), anyString()))
                .thenReturn(Optional.of(new FoundLaughter("test")));

        // run test
        unitUnderTest.getLaughterInstancesFromAudio(getStubAudioVideoMapping());

        verify(pyLaughFinderUtil, never())
                .runPythonLaughFinderScript(anyString(), anyString());
    }

    /**
     * Search for instances using video ID, not audio ID
     */
    @Test
    public void getLaughterInstancesFromAudio_shouldUseVideoIdWhenSearchingForInstances()
    throws Exception
    {
        when(instancePersistenceUtil.getInstancesByBucketAndKey(anyString(), anyString()))
                .thenReturn(Optional.of(new FoundLaughter("test")));

        AudioVideoMapping map = getStubAudioVideoMapping();

        unitUnderTest.getLaughterInstancesFromAudio(map);

        verify(instancePersistenceUtil, atLeastOnce())
                .getInstancesByBucketAndKey(map.getBucket(), map.getVideoFile());
    }

    /**
     * Add StartStop objects as expected
     */
    @Test
    public void addLaughterInstances_shouldAddStartStops()
    throws Exception
    {
        List<long[]> input = getStubInstances();
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

    private List<long[]> getStubInstances() {
        return Arrays.asList(new long[] {111, 222}, new long[]{333, 444});
    }

    private AudioVideoMapping getStubAudioVideoMapping() {
        AudioVideoMapping result = new AudioVideoMapping();
        result.setId((long) 12345);
        result.setBucket("testBucket");
        result.setVideoFile("testVideoFile");
        result.setAudioFile("testAudioFile");
        return result;
    }
}