package edu.uw.citw.service.laughtype;

import edu.uw.citw.persistence.domain.LaughterType;

import java.io.IOException;
import java.util.List;

public interface LaughTypeService {

    List<LaughterType> getAllLaughTypes();

    void addType(String requestJson) throws IOException;
}
