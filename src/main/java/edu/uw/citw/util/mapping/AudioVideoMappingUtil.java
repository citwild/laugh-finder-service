package edu.uw.citw.util.mapping;

import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
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
        List<AudioVideoMapping> searchResult  = audioVideoMappingRepository.findByBucketAndVideo(bucket, key);

        // Expected to be unique result, so always first value
        if (!searchResult.isEmpty()) {
            return Optional.ofNullable(searchResult.get(0));
        }
        return Optional.empty();
    }
}
