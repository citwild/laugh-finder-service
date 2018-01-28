package edu.uw.citw.service.metadata.impl;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.domain.ModelData;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.service.metadata.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * For adding and getting metadata related to specific videos/audio.
 *
 * Created by miles on 2/11/17.
 */
@Service
public class MetadataServiceImpl implements MetadataService {

    private static final Logger log = LoggerFactory.getLogger(MetadataServiceImpl.class);

    private static final String START   = "start";
    private static final String STOP    = "stop";

    private LaughterInstanceRepository laughterInstanceRepository;
    private ModelDataRepository modelDataRepository;

    @Autowired
    public MetadataServiceImpl(
            LaughterInstanceRepository laughterInstanceRepository,
            ModelDataRepository modelDataRepository)
    {
        this.laughterInstanceRepository = laughterInstanceRepository;
        this.modelDataRepository = modelDataRepository;
    }

    @Override
    public JsonNode postMetadataPerInstance() {
        return null;
    }

    public String postNewInstance(Integer videoId, JsonNode val) {
        LaughterInstance submission = new LaughterInstance();

        // set values
        submission.setS3Key(videoId.longValue());
        // times 1000 because DB saves values as milliseconds, not seconds
        submission.setStartTime(
                convertSecondsToMs(val.get(START).asDouble())
        );
        submission.setStopTime(
                convertSecondsToMs(val.get(STOP).asDouble())
        );

        // get current model in use
        List<ModelData> model = modelDataRepository.findByInUse(true);

        // following is true because entered by user
        submission.setAlgCorrect(true);
        submission.setUserMade(true);
        submission.setModelUsed(model.get(0).getId());

        // assume "use for retraining" flag is false, unless explicitly set.
        submission.setUseForRetrain(false);

        LaughterInstance result = laughterInstanceRepository.save(submission);
        return result.toString();
    }

    protected Long convertSecondsToMs(Double sec) {
        return Double.valueOf(sec * 1000).longValue();
    }
}
