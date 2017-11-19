package edu.uw.citw.util.test;

import org.junit.Before;
import org.junit.Test;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import java.util.Random;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;

public class TrainingEngineTest {

    TrainingEngine uut;

    @Before
    public void setUp() throws Exception {
        uut = new TrainingEngine();
    }

    @Test
    public void writeModel_shouldMakeAModel() throws Exception {
        Classifier mockModel = mock(IBk.class);
        byte[] result = uut.serializeModel(mockModel);
        // bad test
        assertThat(result, is(notNull()));
    }

}