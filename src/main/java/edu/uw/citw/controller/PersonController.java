package edu.uw.citw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.IOException;


@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonController() {

    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPersonById(@Nullable @RequestParam String id) throws IOException {
        return null;
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/all/format/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllInJson() throws IOException {
        return null;
    }

    @Nullable
    @ResponseBody
    @GetMapping(value = "/all/format/csv", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getAllInCsv() throws IOException {
        
        return null;
    }
}
