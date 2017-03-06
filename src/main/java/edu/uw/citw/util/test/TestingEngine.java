package edu.uw.citw.util.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copied from Shalini's code.
 * <p>
 * TODO: Can be renamed and refactored as it's integrated into this service.
 * <p>
 * Created by milesdowe on 7/12/16.
 */
@Component("testEngine")
public class TestingEngine {

    private static final Logger log = LoggerFactory.getLogger(TestingEngine.class);

    @Value("${testing.index.path}")
    private String indexPath;
    @Value("${training.model.path}")
    private String modelPath;
    @Value("${testing.window}")
    private String windowSize;

    private String        arffPath;
    private Instances     instances;
    private List<Boolean> isPresentList;

    /**
     * Get the laughter segments from the ARFF files.
     */
    public List<long[]> getLaughters() throws Exception {
        isPresentList = getIsPresentList(indexPath);
        List<long[]> laughtersInMilliSecs = new ArrayList<>();

        try {
            BufferedReader arffReader = new BufferedReader(new FileReader(this.arffPath));
            instances = new Instances(arffReader);
            instances.setClassIndex(instances.numAttributes() - 1);
            Classifier model = (Classifier) weka.core.SerializationHelper
                    .read(modelPath);

            // Test the model
            Evaluation test        = new Evaluation(instances);
            double[]   predictions = test.evaluateModel(model, instances);

            boolean[] isLaughterList = this.merge(predictions, isPresentList);

            log.debug("Laughter time frames...");
            int start, end;

            for (int i = 0; i < isLaughterList.length; i++) {

                // TODO: using "baby giggle" sound, get impossible timeframe (i.e., 0:11.200 to 0:10.400)
                // TODO: false negatives, not recognizing baby giggling (probably unimportant, bad sample)
                if (isLaughterList[i]) {
                    start = i;
                    while (isLaughterList[i] && i < isLaughterList.length - 1) {
                        i++;
                    }
                    end = i;
                    if (end != start + 1) {
                        end--;
                    }
                    long window = Long.parseLong(windowSize);
                    laughtersInMilliSecs.add(new long[] {
                            start * window,
                            end * window
                    });
                    log.debug(getDisplayTime(start * window) + " to " + getDisplayTime(end * window));
                }
            }
            arffReader.close();
        } catch (Exception e) {
            // unfortunate that the WEKA API uses generic Exception...
            log.error("There was a problem", e);
        }
        return laughtersInMilliSecs;
    }

    /**
     * The WEKA instances parsed from the ARFF file.
     */
    public Instances getInstances() {
        return this.instances;
    }

    /**
     * The list of boolean value indicating if the instances in the ARFF file
     * was returned as a laughter segment.
     */
    public List<Boolean> isPresentList() {
        return this.isPresentList;
    }

    /**
     * Merges the predictions with existing instances.
     */
    private boolean[] merge(double[] predictions, List<Boolean> isPresentList) {
        boolean[] isLaughterList   = new boolean[isPresentList.size()];
        int       predictionsIndex = 0;

        for (int i = 0; i < isLaughterList.length; i++) {
            if (isPresentList.get(i)) {
                if (predictions[predictionsIndex] == 0) {
                    isLaughterList[i] = true;
                }
                predictionsIndex++;
            }
        }

        return isLaughterList;
    }

    /**
     * Produces a list of booleans for each instance from the test set
     * indicating if they were identified as laughter.
     */
    private List<Boolean> getIsPresentList(String indexFileName) {
        List<Boolean>  isPresentList = new ArrayList<Boolean>();
        BufferedReader indexFileReader;
        try {
            indexFileReader = new BufferedReader(new FileReader(indexFileName));
            String line;
            while ((line = indexFileReader.readLine()) != null) {
                isPresentList.add(line.trim().equalsIgnoreCase("YES"));
            }
            indexFileReader.close();
        } catch (IOException e) {
            // TODO: handle this better?
            log.error("There was an error", e);
        }

        return isPresentList;
    }

    /**
     * Get the human readable display time as a string.
     */
    private String getDisplayTime(long timeInMilliseconds) {
        double seconds = timeInMilliseconds / 1000.0;
        int    minute  = (int) (seconds / 60);
        double second  = seconds % 60;
        return minute + ":" + String.format("%.3f", second);
    }

    public String getArffPath() {
        return arffPath;
    }

    public void setArffPath(String arffPath) {
        this.arffPath = arffPath;
    }
}
