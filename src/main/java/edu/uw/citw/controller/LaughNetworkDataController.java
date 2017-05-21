package edu.uw.citw.controller;


import edu.uw.citw.service.LaughNetworkDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.IOException;


/**
 * Controller for getting laugh data for network processing
 */
@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/data")
public class LaughNetworkDataController {

    private static final Logger log = LoggerFactory.getLogger(LaughNetworkDataController.class);

    private LaughNetworkDataService service;

    @Autowired
    public LaughNetworkDataController(LaughNetworkDataService service) {
        this.service = service;
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/edges/instance/all/foci/person/format/csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getPersonEngagementEdges_CSV() throws IOException {
        log.debug("Call to: /edges/instance/all/type/engagement/format/csv");
        return service.findPersonEngagementEdges_CSV();
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/edges/instance/all/foci/humor/format/csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getFociEngagementEdges_CSV() throws IOException {
        log.debug("Call to: /edges/instance/all/type/engagement/format/csv");
        return service.findHumorEngagementEdges_CSV();
    }
}
