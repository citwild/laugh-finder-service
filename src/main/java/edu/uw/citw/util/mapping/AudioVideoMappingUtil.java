package edu.uw.citw.util.mapping;

import edu.uw.citw.persistence.domain.AudioVideoMapping;
import edu.uw.citw.persistence.repository.AudioVideoMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Used to get the audio file key for a given bucket video file.
 *
 * Created by Miles on 9/17/2016.
 */
@Component
public class AudioVideoMappingUtil {

    @Autowired
    private AudioVideoMappingRepository audioVideoMappingRepository;

    public String[] getAudioExtractOfVideo(String bucket, String key) {
        String[] extract = new String[2];

        List<AudioVideoMapping> searchResult  = audioVideoMappingRepository.findByBucketAndVideo(bucket, key);

        // Expected to be unique result, so always first value
        if (!searchResult.isEmpty()) {
            AudioVideoMapping mapping = searchResult.get(0);

            extract[0] = bucket;
            extract[1] = mapping.getAudioFile();
        }
        return extract;
    }
}
