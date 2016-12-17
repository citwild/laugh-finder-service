package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.LaughterInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * For interacting with the SAVED_LAUGHTER_TIMESTAMPS table.
 *
 * Created by Miles on 9/25/2016.
 */
public interface LaughterInstanceRepository extends Repository<LaughterInstance, Long>{

    @Query(value = "select * from laugh_instance where s3_key= ?1",
           nativeQuery = true)
    List<LaughterInstance> findByBucketAndKey(String bucket, String key);
}
