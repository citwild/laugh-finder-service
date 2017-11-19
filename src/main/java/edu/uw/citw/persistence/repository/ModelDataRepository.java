package edu.uw.citw.persistence.repository;

import edu.uw.citw.persistence.domain.ModelData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * For interacting with the MODEL_DATA table.
 *
 * Created by Miles on 10/16/2017.
 */
public interface ModelDataRepository extends CrudRepository<ModelData, Long> {

    @Query(value = "select * from model_data md ...",
            nativeQuery = true)
    List<ModelData> findLastId();
}
