package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.InstanceParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the LAUGH_INSTANCE_PARTICIPANTS table.
 *
 * Created by miles on 2/11/17.
 */
public interface InstanceParticipantsRepository extends CrudRepository<InstanceParticipant, Long> {

    @Query(value = "select * from laugh_instance_participants where instance_id = ?1", nativeQuery = true)
    List<InstanceParticipant> findByInstanceId(Long id);
}
