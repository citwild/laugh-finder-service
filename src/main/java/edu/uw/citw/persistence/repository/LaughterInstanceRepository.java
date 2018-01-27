package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.LaughterInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the LAUGH_INSTANCE table.
 *
 * Created by Miles on 9/25/2016.
 */
public interface LaughterInstanceRepository extends CrudRepository<LaughterInstance, Long> {

    @Query(value = "select * from laugh_instance where id = ?1", nativeQuery = true)
    List<LaughterInstance> findById(Long id);

    @Query(value = "select * from laugh_instance where s3_key = ?1 " +
                   "and model_used = (select id from model_data where currently_in_use = 1)", nativeQuery = true)
    List<LaughterInstance> findByS3Key(Long s3Key);

    @Query(value = "select * from laugh_instance where use_for_retrain = 1 " +
                   "and model_used = (select id from model_data where currently_in_use = 1)", nativeQuery = true)
    List<LaughterInstance> getAllMarkedForRetraining();
}