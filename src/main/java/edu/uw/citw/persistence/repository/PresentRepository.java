package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.Present;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresentRepository extends CrudRepository<Present, Long> {

    @Query(value = "select * from present where id = ?1", nativeQuery = true)
    List<Present> findById(Long id);
}
