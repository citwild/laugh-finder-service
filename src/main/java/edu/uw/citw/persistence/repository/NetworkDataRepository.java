package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Returns specialized queries regarding laugh networks.
 *
 * Created by miles on 5/20/17.
 */
@Repository
public interface NetworkDataRepository extends JpaRepository<Person, Long> {

    @Query(value =
        "SELECT (SELECT PERSON_NAME FROM person WHERE ID = pres.PERSON_ID) AS p_to " +
        "  , (SELECT PERSON_NAME FROM person WHERE ID = inst.JOKE_SPEAKER) AS p_from " +
        "  , count(part.PRESENT_ID) / count(pres.ID) * 100 participation_percentage " +
        "FROM present pres " +
        "  INNER JOIN laugh_instance inst " +
        "    ON pres.INSTANCE_ID = inst.ID " +
        "  LEFT JOIN participating part " +
        "    ON pres.ID = part.PRESENT_ID " +
        "GROUP BY p_to, p_from ",
        nativeQuery = true
    )
    List<Object[]> getLaughEngagementRatios();
}
