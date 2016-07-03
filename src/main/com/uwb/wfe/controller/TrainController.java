package com.uwb.wfe.controller;

import com.uwb.wfe.service.train.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Train the model with supervised data.:w
 *
 * Created by Miles on 6/26/2016.
 */
@RestController
@RequestMapping(value = "/train")
public class TrainController {

    private static final Logger log = LoggerFactory.getLogger(TrainController.class);

    private TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }
}
