package edu.uw.citw.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = {"http://localhost:8080", "https://52.37.207.59"})
@RestController
@RequestMapping(value = "/present")
public class PresentController {

}
