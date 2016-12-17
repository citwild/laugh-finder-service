package edu.uw.citw.util.weka;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WekaModelUtilTest {

    private final String inputArffPath = "src/test/resources/data/wekaFile.arff";

    private WekaModelUtil unitUnderTest;

    @Before
    public void setUp() {
        this.unitUnderTest = new WekaModelUtil();
    }

    /* Hacky tests */

    @Test
    public void readArffFile_ShouldProvideTheExpectedInstances() throws Exception {
        Instances result = unitUnderTest.readArffFile(inputArffPath);
        assertEquals(194, result.size());
    }

    @Test
    public void classifyAndSaveModel_ShouldReadTheExpectedFile() throws Exception {
        String expectedString = "IB1 instance-based classifier\nusing 6 nearest neighbour(s) for classification\n";
        IBk result = unitUnderTest.classifyAndGetModel(inputArffPath);
        assertThat(result.toString(), hasToString(expectedString));
    }

    @Test
    public void knnOptions_ShouldBeAsExpected() throws Exception {
        String expected = "-K 6 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A " +
                          "\\\"weka.core.EuclideanDistance -R first-last\\\"\"";
        assertEquals(expected, unitUnderTest.getKnnOptions());
    }
}
