package edu.uw.citw.service.train.impl;

import edu.uw.citw.util.weka.WekaModelUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class TrainServiceImplTest {

    private WekaModelUtil wekaModelUtil;

    private TrainServiceImpl unitUnderTest;

    @Before
    public void setUp() throws Exception {
        wekaModelUtil = mock(WekaModelUtil.class);
        unitUnderTest = new TrainServiceImpl(wekaModelUtil);
    }

    @Test
    public void trainModel_Should() throws Exception {
        // only temporary...
        assertTrue(true == true);
    }
}