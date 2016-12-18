package edu.uw.citw.util.persistence;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * Used to retrieve and format data from databases
 *
 * Created by Miles on 12/17/2016.
 */
@Component
public class InstancePersistenceUtil {

    private Logger log = LoggerFactory.getLogger(InstancePersistenceUtil.class);

    private AudioVideoMappingRepository audioVideoMapping;
    private LaughterInstanceRepository laughterInstance;

    public InstancePersistenceUtil(
            AudioVideoMappingRepository audioVideoMapping,
            LaughterInstanceRepository laughterInstance) {

        this.audioVideoMapping = audioVideoMapping;
        this.laughterInstance = laughterInstance;
    }

    public Optional<FoundLaughter> getInstancesByBucketAndKey(String bucket, String key) {
        // get ID of key/bucket combo
        List<AudioVideoMapping> resultSet = audioVideoMapping.findByBucketAndVideo(bucket, key);

        if (resultSet.size() != 1) {
            log.warn("More than one result was returned for the given S3 key/bucket combination.");
            return Optional.empty();
        }

        Long targetId = resultSet.get(0).getId();

        // get instances using ID & provide result
        List<LaughterInstance> instances = laughterInstance.findById(targetId);
        FoundLaughter foundLaughter = null;

        if (!CollectionUtils.isEmpty(instances)) {
            foundLaughter = new FoundLaughter(bucket + "/" + key);

            for (LaughterInstance instance : instances) {
                foundLaughter.addStartStop(instance);
            }
        }
        return Optional.ofNullable(foundLaughter);
    }
}
