package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query(value = "select * from person", nativeQuery = true)
    List<Person> getAll();
}
