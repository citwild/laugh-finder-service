package edu.uw.citw.service;

import com.fasterxml.jackson.databind.JsonNode;
import edu.uw.citw.persistence.domain.Person;
import edu.uw.citw.persistence.repository.PersonRepository;
import edu.uw.citw.util.JsonNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private static final String CSV_SEP = ",";

    private PersonRepository repository;
    private JsonNodeAdapter jsonAdapter;

    @Autowired
    public PersonService(PersonRepository repository, JsonNodeAdapter jsonAdapter) {
        this.repository = repository;
        this.jsonAdapter = jsonAdapter;
    }

    /**
     * Return all people in JSON format
     */
    public String getAllInJson() {
        try {
            List<Person> people = repository.getAll();
            return jsonAdapter.createJsonArray("people", people);
        }
        catch (Exception e) {
            log.error("Failure reaching database", e);
        }
        return "";
    }

    /**
     * Return all people in CSV format
     */
    public String getAllInCsv() {
        StringBuilder b = new StringBuilder("");
        try {
            List<Person> people = repository.getAll();
            for(Person person : people) {
                b.append(person.getId())
                        .append(CSV_SEP)
                        .append(person.getName())
                        .append(CSV_SEP)
                        .append(person.getRole())
                        .append(CSV_SEP)
                        .append(person.getSex())
                        .append('\n');
            }
        }
        catch (Exception e) {
            log.error("Failure reaching database", e);
        }
        return b.toString();
    }

}
