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
    private List<StartStop> instances;

    public FoundLaughter(String filename) {
        this.filename = filename;
        this.instances = new ArrayList<>();
    }

    public void addStartStop(long start, long stop) {
        instances.add(new StartStop(start, stop));
    }

    public void addStartStop(LaughterInstance instance) {
        addStartStop(instance.getStartTime(), instance.getStopTime());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<StartStop> getInstances() {
        return instances;
    }

    public void setInstances(List<StartStop> instances) {
        this.instances = instances;
    }
}
