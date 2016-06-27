package com.uwb.wfe.util.extract;

import com.uwb.wfe.util.EnvUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FfmpegAdapterTest {

    FfmpegAdapter unitUnderTest;

    EnvUtil envUtil;

    @Before
    public void setUp() {
        this.envUtil = Mockito.mock(EnvUtil.class);
        this.unitUnderTest = new FfmpegAdapter(envUtil);

        // initialize @Value fields
        ReflectionTestUtils.setField(unitUnderTest, "winFfmpegCmd", "C:\\Program Files (x86)\\ffmpeg\\bin\\ffmpeg.exe");
        ReflectionTestUtils.setField(unitUnderTest, "nixFfmpegCmd", "/bin/ffmpeg");
    }

    // TODO: don't hardcode things like this...
    @Test
    public void extractAudio_ShouldCallTheCorrectPathIfWindows() throws Exception {
        String input =  "../test-videos/test-vid.mp4";
        String output = "../test-videos/test-audio.wav";
        when(envUtil.isWindows()).thenReturn(true);

        unitUnderTest.extractAudio(input, output);

        File result = new File("../test-videos/test-audio.wav");
        Assert.assertTrue(result.canRead());
    }
}
