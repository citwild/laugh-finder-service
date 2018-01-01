package edu.uw.citw.util.test;

import org.junit.Before;
import org.junit.Test;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import static org.hamcrest.Matchers.notNullValue;

public class TrainingEngineTest {

    TrainingEngine uut;

    @Before
    public void setUp() throws Exception {
        uut = new TrainingEngine();
    }

    @Test
    public void serializeModel_shouldMakeAModel() throws Exception {
        Classifier mockModel = mock(IBk.class);
        byte[] result = uut.serializeModel(mockModel);
        // bad test
        assertThat(result, notNullValue());
    }

}