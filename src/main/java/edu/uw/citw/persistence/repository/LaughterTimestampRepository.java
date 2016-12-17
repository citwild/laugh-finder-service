package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.LaughterTimestamp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * For interacting with the SAVED_LAUGHTER_TIMESTAMPS table.
 *
 * Created by Miles on 9/25/2016.
 */
public interface LaughterTimestampRepository extends Repository<LaughterTimestamp, Long>{

    @Query(value = "select * from saved_laughter_timestamps times where times.s3_bucket = ?1 and times.s3_key = ?2",
           nativeQuery = true)
    List<LaughterTimestamp> findByBucketAndKey(String bucket, String key);
}
