package edu.uw.citw.service.analyze.impl;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.model.LaughInstance;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.ModelData;
import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.persistence.InstancePersistenceUtil;
import edu.uw.citw.util.pylaughfinder.PyLaughFinderUtil;
import edu.uw.citw.util.test.TestingEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeServiceImplTest {

    private TestingEngine te;
    private InstancePersistenceUtil ipu;
    private PyLaughFinderUtil plfu;
    private JsonNodeAdapter jna;
    private ModelDataRepository mdr;

    private AnalyzeServiceImpl unitUnderTest;

    @Before
    public void setUp()
    throws Exception
    {
        te = mock(TestingEngine.class);
        ipu = mock(InstancePersistenceUtil.class);
        plfu = mock(PyLaughFinderUtil.class);
        jna = mock(JsonNodeAdapter.class);
        mdr = mock(ModelDataRepository.class);

        unitUnderTest = new AnalyzeServiceImpl(te, ipu, plfu, jna, mdr);
    }

    /**
     * Run python code if laugh instances don't exist
     */
    @Test
    public void getLaughterInstancesFromAudio_shouldRunPythonCode_ifDatabaseFindsNothing()
    throws Exception
    {
        // pretend database didn't find anything
        when(ipu.getInstancesByBucketAndKey(anyString(), anyString()))
                .thenReturn(Optional.empty());
        // pretend python code is run
        when(plfu.runPythonLaughFinderScript(anyString(), anyString()))
                .thenReturn(null);
        when(te.getLaughters())
                .thenReturn(getStubInstances());
        when(mdr.findByInUse(true))
                .thenReturn(Collections.singletonList(new ModelData()));

        // run test
        unitUnderTest.getLaughterInstancesFromAudio(getStubAudioVideoMapping());

        verify(plfu, times(1))
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
        when(ipu.getInstancesByBucketAndKey(anyString(), anyString()))
                .thenReturn(Optional.of(new FoundLaughter("test")));

        // run test
        unitUnderTest.getLaughterInstancesFromAudio(getStubAudioVideoMapping());

        verify(plfu, never())
                .runPythonLaughFinderScript(anyString(), anyString());
    }

    /**
     * Search for instances using video ID, not audio ID
     */
    @Test
    public void getLaughterInstancesFromAudio_shouldUseVideoIdWhenSearchingForInstances()
    throws Exception
    {
        when(ipu.getInstancesByBucketAndKey(anyString(), anyString()))
                .thenReturn(Optional.of(new FoundLaughter("test")));

        AudioVideoMapping map = getStubAudioVideoMapping();

        unitUnderTest.getLaughterInstancesFromAudio(map);

        verify(ipu, atLeastOnce())
                .getInstancesByBucketAndKey(map.getBucket(), map.getVideoFile());
    }

    /**
     * Add LaughInstance objects as expected
     */
    @Test
    public void addLaughterInstances_shouldAddStartStops()
    throws Exception
    {
        List<long[]> input = getStubInstances();
        FoundLaughter result = new FoundLaughter("testFile");

        unitUnderTest.addLaughterInstances(input, result);

        assertEquals(2, result.getInstances().size());

        LaughInstance first = result.getInstances().get(0);
        assertEquals(111, first.getStart());
        assertEquals(222, first.getStop());

        LaughInstance second = result.getInstances().get(1);
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