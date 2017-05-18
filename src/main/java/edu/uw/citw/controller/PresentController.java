package edu.uw.citw.controller;

import edu.uw.citw.service.PresentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/present")
public class PresentController {
    private static final Logger log = LoggerFactory.getLogger(LaughNetworkDataController.class);

    private PresentService service;

    @Autowired
    public PresentController(PresentService service) {
        this.service = service;
    }

// Insert present value
    @Nullable
    @ResponseBody
    @PostMapping(value = "/add")
    public String addPresentPerson() throws IOException {
        return null;
    }

// Retrieve present

}
