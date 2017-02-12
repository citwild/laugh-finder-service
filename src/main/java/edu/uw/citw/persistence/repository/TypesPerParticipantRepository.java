package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.ParticipantType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the LAUGH_TYPES_PER_PARTICIPANT table.
 *
 * Created by miles on 2/12/17.
 */
public interface TypesPerParticipantRepository extends CrudRepository<ParticipantType, Long> {

    @Query(value = "select * from laugh_types_per_participant where participant_id = ?1", nativeQuery = true)
    List<ParticipantType> findByParticipantId(Long id);
}
