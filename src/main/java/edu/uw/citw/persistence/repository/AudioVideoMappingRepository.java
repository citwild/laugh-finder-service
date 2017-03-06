package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.AudioVideoMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * For interacting with the TRANSCODER_VID_AUD_MAPPINGS table.
 *
 * Created by Miles on 9/17/2016.
 */
public interface AudioVideoMappingRepository extends Repository<AudioVideoMapping, Long> {

    @Query(value = "select * from transcoder_vid_aud_mappings map where map.bucket = ?1 and map.mp4_file = ?2",
           nativeQuery = true)
    List<AudioVideoMapping> findByBucketAndVideo(String bucket, String videoKey);
}
