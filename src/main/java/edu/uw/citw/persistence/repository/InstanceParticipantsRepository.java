package edu.uw.citw.persistence.repository;

import org.springframework.data.repository.CrudRepository;

/**
 * For interacting with the LAUGH_INSTANCE_PARTICIPANTS table.
 *
 * Created by miles on 2/11/17.
 */
public interface InstanceParticipantsRepository extends CrudRepository<InstanceParticipant, Long> {

}
