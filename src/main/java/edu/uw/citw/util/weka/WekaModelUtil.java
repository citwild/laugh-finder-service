package edu.uw.citw.util.weka;

import org.springframework.stereotype.Component;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

import javax.annotation.Nonnull;


/**
 * Weka is a data-mining tool developed by the University of Waikato. This util uses the Weka API to translate
 * the *.arff file generated by the "training" Python script into a usable model.
 *
 * Created by milesdowe on 7/4/16.
 */
@Component
public class WekaModelUtil {

    // copied from the Weka app when building the model manually
    private final String KNN_OPTIONS = "-K 6 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A " +
                                       "\\\"weka.core.EuclideanDistance -R first-last\\\"\"";


    public Instances readArffFile(@Nonnull String filepath) throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(filepath);

        Instances data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1);
        data.setRelationName("Laughter_detection_capture_training");
        return data;
    }

    public IBk classifyAndGetModel(@Nonnull String filepath) throws Exception {
        Instances data = readArffFile(filepath);
        // set classifier
        IBk iBk = new IBk();
        // establish algorithm options
        iBk.setOptions(weka.core.Utils.splitOptions(KNN_OPTIONS));
        // train
        iBk.buildClassifier(data);

        return iBk;
    }

    public void saveModel(@Nonnull String modelOutputPath, @Nonnull IBk iBk) throws Exception {
        SerializationHelper.write(modelOutputPath, iBk);
    }

    public String getKnnOptions() {
        return KNN_OPTIONS;
    }
}
