package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {

    @Query(value = "select * from laugh_instance_participants where instance_id = ?1", nativeQuery = true)
    List<Participant> findByInstanceId(Long id);
}
