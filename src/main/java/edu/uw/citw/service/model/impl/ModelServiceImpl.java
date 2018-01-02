package edu.uw.citw.service.model.impl;

import edu.uw.citw.persistence.repository.ModelDataRepository;
import edu.uw.citw.service.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    private static final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private ModelDataRepository modelRepository;

    @Autowired
    public ModelServiceImpl(ModelDataRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public String getAllModels() {
        return null;
    }

    @Override
    public String switchModel(Long id) {
        return null;
    }

    @Override
    public String retrainNewModel() {
        return null;
    }
}
