package edu.uw.citw.util.test;

import edu.uw.citw.exception.ArffGenerationException;
import edu.uw.citw.exception.ModelGenerationException;
import edu.uw.citw.persistence.domain.ModelData;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

import java.io.*;
import java.util.Random;

/**
 * Class responsible for generating a new *.model file.
 * <p>
 * Created by milesdowe on 7/12/16.
 */
public class TrainingEngine {

    public ModelData createNewModel(String oldArff, Instances newInstances, String createdBy)
    throws ArffGenerationException, ModelGenerationException {
        // create new db object
        ModelData newModel = new ModelData();

        // update arff
        String arff;
        try {
            arff = generateArff(oldArff, newInstances);
        } catch (IOException e) {
            throw new ArffGenerationException(e);
        }

        // create model from arff
        byte[] model;
        try {
            model = generateModel(arff);
        } catch (Exception e) {
            throw new ModelGenerationException(e);
        }

        // add both to db object
        newModel.setArffData(arff);
        newModel.setModelBinary(model);
        newModel.setCreatedBy(createdBy);

        return newModel;
    }


    String generateArff(String oldArff, Instances newInstances) throws IOException {
        // load current instances (previous ARFF file?)
        BufferedReader arffIn = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(oldArff.getBytes())
                )
        );
        Instances currInstances = new Instances(arffIn);

        // get last placement in list of instances
        currInstances.setClassIndex(
                getLastAttributeIndex(currInstances)
        );

        // add all of the new instances
        for (int i = 0; i < newInstances.numInstances(); i++) {
            currInstances.add(newInstances.instance(i));
        }

        // return the updated set of instances
        return currInstances.toString();
    }

    byte[] generateModel(String arffData) throws Exception {
        byte[] result = new byte[]{};

        Instances instances = new Instances(
                new BufferedReader(
                        new StringReader(arffData)
                )
        );
        instances.setClassIndex(getLastAttributeIndex(instances));

        Classifier ibk = new IBk(2);
        Evaluation eval = new Evaluation(instances);
        eval.crossValidateModel(ibk,instances, 10, new Random(1));
        ibk.buildClassifier(instances);

        return serializeModel(ibk);
    }

    byte[] serializeModel(Classifier model) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(model);
            }
            return b.toByteArray();
        }
    }

    private int getLastAttributeIndex(Instances inst) {
        return inst.numAttributes() - 1;
    }
}
