package edu.uw.citw.model;

import edu.uw.citw.persistence.domain.Participant;

import java.util.List;

/**
 * Represents an individual participant detected in a laughter instance.
 *
 * Created by miles on 2/11/17.
 */
public class LaughParticipant {

    private long id;
    private String name;
    private List<String> tags;
    private int intensity;

    public LaughParticipant() {}

    public LaughParticipant(String name) {
        this.name = name;
    }

    public LaughParticipant(String name, int intensity) {
        this.name = name;
        this.intensity = intensity;
    }

    public LaughParticipant(String name, List<String> tags, int intensity) {
        this.name = name;
        this.tags = tags;
        this.intensity = intensity;
    }

    public LaughParticipant(Participant participant) {
        this.name = participant.getParticipantName();
        this.intensity = participant.getIntensity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LaughParticipant that = (LaughParticipant) o;

        if (id != that.id) return false;
        if (intensity != that.intensity) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + intensity;
        return result;
    }

    @Override
    public String toString() {
        return "LaughParticipant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", intensity=" + intensity +
                '}';
    }
}
