package edu.uw.citw.util.persistence;

import edu.uw.citw.model.FoundLaughter;
import edu.uw.citw.model.StartStop;
import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.domain.LaughterInstance;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import edu.uw.citw.persistence.repository.LaughterInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
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

    private AudioVideoMappingRepository audioVideoMappingRepository;
    private LaughterInstanceRepository laughterInstanceRepository;

    public InstancePersistenceUtil(
            AudioVideoMappingRepository audioVideoMappingRepository,
            LaughterInstanceRepository laughterInstanceRepository)
    {
        this.audioVideoMappingRepository = audioVideoMappingRepository;
        this.laughterInstanceRepository = laughterInstanceRepository;
    }

    public Optional<FoundLaughter> getInstancesByBucketAndKey(
            @Nonnull String bucket,
            @Nonnull String key)
    {
        // get ID of key/bucket combo
        List<AudioVideoMapping> resultSet = audioVideoMappingRepository.findByBucketAndVideo(bucket, key);

        // should only get one asset per bucket/key combination; else, doesn't exist or weird collision
        if (resultSet.size() != 1) {
            log.warn("More than one result was returned for the given S3 key/bucket combination:");
            log.warn("\tResult size = {}", resultSet.size());
            return Optional.empty();
        }

        // get instances using ID & provide result
        Long targetId = resultSet.get(0).getId();
        List<LaughterInstance> instances = laughterInstanceRepository.findById(targetId);
        FoundLaughter foundLaughter = null;

        if (!CollectionUtils.isEmpty(instances)) {
            foundLaughter = new FoundLaughter(bucket + "/" + key);

            for (LaughterInstance instance : instances) {
                foundLaughter.addStartStop(instance);
            }
        }
        return Optional.ofNullable(foundLaughter);
    }

    public void saveInstances(
            @Nonnull FoundLaughter foundLaughter,
            long dbId)
    {
        for (StartStop startStop : foundLaughter.getInstances()) {
            laughterInstanceRepository.save(createInstance(startStop, dbId));
        }
    }

    public LaughterInstance createInstance(
            @Nonnull StartStop startStop,
            long dbId)
    {
        return new LaughterInstance(null, dbId, startStop.getStart(), startStop.getStop());
    }
}
