package edu.uw.citw.service.model;

public interface ModelService {

    String getAllModels();

    String switchModel(Long id);

    String retrainNewModel();
}
