package edu.uw.citw.util.persistence;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.model.LaughInstance;
import edu.uw.citw.persistence.domain.*;
import edu.uw.citw.persistence.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Used to retrieve and format data from databases
 *
 * Created by Miles on 12/17/2016.
 */
@Component
public class InstancePersistenceUtil {

    private Logger log = LoggerFactory.getLogger(InstancePersistenceUtil.class);

    private AudioVideoMappingRepository audioVideoMappingRepository;
    private LaughterInstanceRepository laughterInstanceRepository;
    private LaughTypesRepository laughTypesRepository;

    @Autowired
    public InstancePersistenceUtil(
            AudioVideoMappingRepository audioVideoMappingRepository,
            LaughterInstanceRepository laughterInstanceRepository,
            LaughTypesRepository laughTypesRepository)
    {
        this.audioVideoMappingRepository = audioVideoMappingRepository;
        this.laughterInstanceRepository = laughterInstanceRepository;
        this.laughterInstanceRepository = laughterInstanceRepository;
        this.laughTypesRepository = laughTypesRepository;
    }

    public Optional<FoundLaughter> getInstancesByBucketAndKey(@Nonnull String bucket, @Nonnull String key) {
        // get ID of key/bucket combo
        List<AudioVideoMapping> resultSet = audioVideoMappingRepository.findByBucketAndVideo(bucket, key);

        // should only get one asset per bucket/key combination; else, doesn't exist or weird collision
        if (resultSet.size() != 1) {
            log.warn("More than one result was returned for the given S3 key/bucket combination:");
            log.warn("\tResult size = {}", resultSet.size());
            return Optional.empty();
        }

        // get instances using ID & provide result
        Long s3Key = resultSet.get(0).getId();
        List<LaughterInstance> instances = laughterInstanceRepository.findByS3Key(s3Key);
        FoundLaughter foundLaughter = null;

        // database contains values, begin modelling
        if (!CollectionUtils.isEmpty(instances)) {

            // get types
            Map<Long, String> typesById = createLaughTypeMap();

            foundLaughter = new FoundLaughter(bucket + "/" + key);
            foundLaughter.setVideoId(s3Key);

            // get the participants for each instance (should be some)
            for (LaughterInstance instance : instances) {
                // get participants for this instance
                LaughInstance result = new LaughInstance(instance);
                foundLaughter.addInstance(result);
            }
        }
        return Optional.ofNullable(foundLaughter);
    }

    private Map<Long, String> createLaughTypeMap() {
        Map<Long, String> result = new HashMap<>();

        List<LaughterType> types = laughTypesRepository.findAll();
        for (LaughterType type : types) {
            // add type value if "considered"
            if (type.getConsidered()) {
                result.put(type.getId(), type.getType());
                log.debug("Adding laugh type \"{}\" to map of types", type.getType());
            } else {
                log.debug("Type \"{}\" is marked as unconsidered; not adding", type.getType());
            }
        }

        return result;
    }

    public void saveInstances(@Nonnull FoundLaughter foundLaughter, long dbId) {
        for (LaughInstance laughInstance : foundLaughter.getInstances()) {
            laughterInstanceRepository.save(createInstance(laughInstance, dbId));
        }
    }

    public LaughterInstance createInstance(@Nonnull LaughInstance laughInstance, long dbId) {
        return new LaughterInstance(null, dbId, laughInstance.getStart(), laughInstance.getStop());
    }
}
