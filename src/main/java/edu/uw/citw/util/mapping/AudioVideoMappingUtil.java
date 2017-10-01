package edu.uw.citw.util.mapping;

import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * Used to get the audio file key for a given bucket video file.
 *
 * Created by Miles on 9/17/2016.
 */
@Component
public class AudioVideoMappingUtil {

    private static final Logger log = LoggerFactory.getLogger(AudioVideoMappingUtil.class);

    private AudioVideoMappingRepository audioVideoMappingRepository;

    @Autowired
    public AudioVideoMappingUtil(
            AudioVideoMappingRepository audioVideoMappingRepository)
    {
        this.audioVideoMappingRepository = audioVideoMappingRepository;
    }

    public Optional<AudioVideoMapping> getAudioExtractOfVideo(
            @Nonnull String bucket,
            @Nonnull String key)
    {
        log.debug("Finding instances for: bucket={}, key={}", bucket, key);
        List<AudioVideoMapping> searchResult  = audioVideoMappingRepository.findByBucketAndVideo(bucket, key);

        // Expected to be unique result, so always first value
        if (!searchResult.isEmpty()) {
            return Optional.ofNullable(searchResult.get(0));
        }
        return Optional.empty();
    }

    public Optional<AudioVideoMapping> getAudioExtractOfVideo(@Nonnull Integer id) {
        List<AudioVideoMapping> searchResult  = audioVideoMappingRepository.findById(id);

        // Expected to be unique result, so always first value
        if (!searchResult.isEmpty()) {
            return Optional.ofNullable(searchResult.get(0));
        }
        return Optional.empty();
    }
}
