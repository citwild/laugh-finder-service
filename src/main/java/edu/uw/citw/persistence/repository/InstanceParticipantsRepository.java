package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the LAUGH_INSTANCE_PARTICIPANTS table.
 *
 * Created by miles on 2/11/17.
 */
public interface InstanceParticipantsRepository extends CrudRepository<Participant, Long> {

    @Query(value = "select * from participanting where instance_id = ?1", nativeQuery = true)
    List<Participant> findByInstanceId(Long id);
}
