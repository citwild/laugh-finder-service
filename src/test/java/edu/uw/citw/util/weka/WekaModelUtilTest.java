package edu.uw.citw.util.weka;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WekaModelUtilTest {

    private static final String TEST_FILE_DIR = "testFiles";
    private final String inputArffPath = "src/test/resources/data/wekaFile.arff";

    private WekaModelUtil uut;

    @Before
    public void setUp() {
        this.uut = new WekaModelUtil();
    }

    /* Hacky tests */

    @Test
    public void readArffFile_ShouldProvideTheExpectedInstances() throws Exception {
        Instances result = uut.readArff(inputArffPath);
        assertEquals(194, result.size());
    }

    @Test
    public void classifyAndGetModel_ShouldReadTheExpectedFile() throws Exception {
        String expectedString = "IB1 instance-based classifier\nusing 6 nearest neighbour(s) for classification\n";
        IBk result = uut.classifyAndGetModel(inputArffPath);
        assertThat(result.toString(), hasToString(expectedString));
    }

    @Test
    public void saveModel_ShouldExistProbably() throws Exception {
        // create junk directory for output
        File tmpDir = new File(TEST_FILE_DIR);
        tmpDir.mkdir();

        uut.saveModel(TEST_FILE_DIR + "/testModel.model", new IBk());

        // delete directory & resulting file
        File testFilesDir = new File(TEST_FILE_DIR);
        for (File file : testFilesDir.listFiles()) {
            file.delete();
        }
        testFilesDir.delete();
    }
}
