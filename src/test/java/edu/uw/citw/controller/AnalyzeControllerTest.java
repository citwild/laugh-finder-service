package edu.uw.citw.controller;

import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.mapping.AudioVideoMappingUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AnalyzeControllerTest {

    private AnalyzeController unitUnderTest;

    private AnalyzeService analyzeService;
    private AudioVideoMappingUtil audioVideoMappingUtil;

    @Before
    public void setUp() throws Exception {
        this.analyzeService = mock(AnalyzeService.class);
        this.audioVideoMappingUtil = mock(AudioVideoMappingUtil.class);

        this.unitUnderTest = new AnalyzeController(this.analyzeService, this.audioVideoMappingUtil);
    }

    @Test
    public void analyzeVideo_shouldNotAttempAnything_ifProvidedNullArguments() throws Exception {
        unitUnderTest.analyzeVideoByBucketAndKey(null, "hey");
        verify(audioVideoMappingUtil, never()).getAudioExtractOfVideo(any(), any());

        unitUnderTest.analyzeVideoByBucketAndKey("hey", null);
        verify(audioVideoMappingUtil, never()).getAudioExtractOfVideo(any(), any());
    }

    @Test
    public void analyzeVideo_shouldReturnNull_ifProvidedNullArguments() throws Exception {
        String result = unitUnderTest.analyzeVideoByBucketAndKey(null, "hey");
        assertEquals(null, result);
        result = unitUnderTest.analyzeVideoByBucketAndKey("hey", null);
        assertEquals(null, result);
    }
}