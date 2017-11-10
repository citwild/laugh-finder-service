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

    private String              filename;
    private Long                videoId;
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
                    true,
                    false
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
                    instance.getAlgCorrect(),
                    instance.getUserMade()
                )
        );
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoundLaughter that = (FoundLaughter) o;

        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (videoId != null ? !videoId.equals(that.videoId) : that.videoId != null) return false;
        return instances != null ? instances.equals(that.instances) : that.instances == null;
    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + (videoId != null ? videoId.hashCode() : 0);
        result = 31 * result + (instances != null ? instances.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FoundLaughter{" +
                "filename='" + filename + '\'' +
                ", videoId=" + videoId +
                ", instances=" + instances +
                '}';
    }
}
