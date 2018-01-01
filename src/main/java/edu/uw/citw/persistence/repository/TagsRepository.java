package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the LAUGH_INSTANCE_TYPES table.
 *
 * Created by miles on 12/28/17.
 */
public interface TagsRepository extends CrudRepository<Tag, Long> {

    @Query(value = "select * from laugh_instance_tags where instance_id = ?1", nativeQuery = true)
    List<Tag> findAllPerInstance(Long instanceId);

    void deleteTagsByInstanceIdEquals(Long instanceId);
}
