package com.uwb.wfe.util.extract;

import com.uwb.wfe.util.EnvUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Miles on 6/26/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class FfmpegAdapterTest {

    FfmpegAdapter unitUnderTest;

    EnvUtil envUtil;

    @Before
    public void setUp() {
        this.envUtil = Mockito.mock(EnvUtil.class);
        this.unitUnderTest = new FfmpegAdapter(envUtil);
    }
}
