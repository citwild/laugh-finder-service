package edu.uw.citw.service.train;

import java.io.IOException;

/**
 * Handles work executing the Laugh Finder algorithm with supervised data.
 *
 * Created by Miles on 7/2/2016.
 */
public interface TrainService {

    void trainModel() throws IOException, InterruptedException;
}
