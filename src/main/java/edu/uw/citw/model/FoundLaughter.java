package edu.uw.citw.model;

import edu.uw.citw.persistence.domain.LaughterInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * A model representing the found laughter instances in video/audio file.
 *
 * Created by milesdowe on 7/12/16.
 */
public class FoundLaughter {

    private String          filename;
    private List<LaughInstance> instances;

    public FoundLaughter(String filename) {
        this.filename = filename;
        this.instances = new ArrayList<>();
    }

    public void addInstance(long start, long stop) {
        instances.add(
                new LaughInstance(
                    start,
                    stop,
                    new ArrayList<>(),
                    false,
                    null,
                    true
                )
        );
    }

    public void addInstance(LaughterInstance instance) {
        instances.add(
                new LaughInstance(
                    instance.getStartTime(),
                    instance.getStopTime(),
                    new ArrayList<>(),
                    instance.getJoke(),
                    instance.getJokeSpeaker(),
                    instance.getAlgCorrect()
                )
        );
    }

    public void addInstance(LaughInstance instance) {
        instances.add(instance);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<LaughInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<LaughInstance> instances) {
        this.instances = instances;
    }
}
