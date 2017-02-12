package edu.uw.citw.service.laughtype.impl;

import edu.uw.citw.persistence.domain.LaughterType;
import edu.uw.citw.persistence.repository.LaughTypesRepository;
import edu.uw.citw.service.laughtype.LaughTypeService;
import edu.uw.citw.util.JsonNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * For getting laugh types.
 *
 * Created by miles on 2/12/17.
 */
@Service
public class LaughTypeServiceImpl implements LaughTypeService {

    private static final Logger log = LoggerFactory.getLogger(LaughTypeServiceImpl.class);

    private LaughTypesRepository laughTypesRepository;
    private JsonNodeAdapter jsonNodeAdapter;

    @Autowired
    public LaughTypeServiceImpl(LaughTypesRepository laughTypesRepository, JsonNodeAdapter jsonNodeAdapter) {
        this.laughTypesRepository = laughTypesRepository;
        this.jsonNodeAdapter = jsonNodeAdapter;
    }

    @Override
    public List<LaughterType> getAllLaughTypes() {
        List<LaughterType> types = laughTypesRepository.findAll();
        types.forEach(type -> log.debug(type.toString()));
        return types;
    }
}
