package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.LaughterType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the LAUGH_TYPES table.
 *
 * Created by miles on 2/11/17.
 */
public interface LaughTypesRepository extends CrudRepository<LaughterType, Long> {

    @Query(value = "select * from laugh_types where considered = 1", nativeQuery = true)
    List<LaughterType> findAllConsidered();

    @Query(value = "select * from laugh_types", nativeQuery = true)
    List<LaughterType> findAll();

    @Query(value = "select * from laugh_types where id = ?1", nativeQuery = true)
    List<LaughterType> findById(Long id);
}
