package com.uwb.wfe.service.train.impl;

import com.uwb.wfe.service.train.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Allows users to train the model.
 *
 * Created by Miles on 7/3/2016.
 */
@Service
public class TrainServiceImpl implements TrainService {

    private static Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    @Override
    public void trainModel() {

    }
}
