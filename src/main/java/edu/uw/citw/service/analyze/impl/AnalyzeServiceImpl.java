package edu.uw.citw.service.analyze.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.service.analyze.AnalyzeService;
import edu.uw.citw.util.JsonNodeAdapter;
import edu.uw.citw.util.persistence.InstancePersistenceUtil;
import edu.uw.citw.util.pylaughfinder.PyLaughFinderUtil;
import edu.uw.citw.util.test.TestingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import weka.experiment.InstancesResultListener;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of the Analyze Service.
 * <p>
 * Created by milesdowe on 7/12/16.
 */
@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    private static final Logger log                   = LoggerFactory.getLogger(AnalyzeServiceImpl.class);
    private static final String FOUND_LAUGHTERS_LABEL = "foundLaughters";

    private TestingEngine   testEngine;
    private InstancePersistenceUtil instancePersistenceUtil;
    private PyLaughFinderUtil pyLaughFinderUtil;
    private JsonNodeAdapter jsonNodeAdapter;

    // External files relating to WEKA and the learning python script

    @Value("${testing.arff.path}")
    private String arffLocation;

    @Autowired
    public AnalyzeServiceImpl(
            TestingEngine testEngine,
            InstancePersistenceUtil instancePersistenceUtil,
            PyLaughFinderUtil pyLaughFinderUtil,
            JsonNodeAdapter jsonNodeAdapter)
    {
        this.testEngine = testEngine;
        this.instancePersistenceUtil = instancePersistenceUtil;
        this.pyLaughFinderUtil = pyLaughFinderUtil;
        this.jsonNodeAdapter = jsonNodeAdapter;
    }

    @Override
    public JsonNode getLaughterInstancesFromAudio(
            @Nonnull AudioVideoMapping mapping)
    throws IOException
    {
        // first, try to get any existing instances
        log.debug("Checking for existing timestamps");
        Optional<FoundLaughter> result = instancePersistenceUtil
                .getInstancesByBucketAndKey(mapping.getBucket(), mapping.getVideoFile());

        // otherwise, laughter instances don't exist; will run algorithm
        if (!result.isPresent()) {
            log.debug("No pre-existing timestamps found; running search algorithm");
            result = findLaughterInstances(mapping.getBucket(), mapping.getAudioFile(), mapping.getId());
        }

        return jsonNodeAdapter.createJsonObject(
                FOUND_LAUGHTERS_LABEL,
                (result.isPresent()) ? result.get() : null
        );
    }

    @Nonnull
    public Optional<FoundLaughter> findLaughterInstances(
            @Nonnull String bucket,
            @Nonnull String key,
            @Nonnull long dbId)
    throws IOException
    {
        log.debug("Beginning search for laughter in a given bucket/key");
        // prepare response; label is bucket and key
        FoundLaughter result = new FoundLaughter(bucket + "/" + key);

        pyLaughFinderUtil.runPythonLaughFinderScript(bucket, key);

        log.debug("Getting laughter from arff file, returning result");
        testEngine.setArffPath(arffLocation);
        try {
            List<long[]> laughterList = testEngine.getLaughters();
            result = addLaughterInstances(laughterList, result);

            // add instances to DB for quick retrieval
            instancePersistenceUtil.saveInstances(result, dbId);

            return Optional.of(result);

        } catch (Exception e) {
            log.error("There was an error searching for laughter in audio file: {}", key, e);
            return Optional.empty();
        }
    }

    /**
     * Appends the provided laughters to the provided result
     */
    @Nonnull
    public FoundLaughter addLaughterInstances(@Nonnull List<long[]> laughterList, @Nonnull FoundLaughter result) {
        for (long[] laughter : laughterList) {
            long start = laughter[0];
            long stop  = laughter[1];
            result.addStartStop(start, stop);
        }
        return result;
    }

    public String getArffLocation() {
        return arffLocation;
    }

    public void setArffLocation(String arffLocation) {
        this.arffLocation = arffLocation;
    }

}
