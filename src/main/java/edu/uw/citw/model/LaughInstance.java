package edu.uw.citw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * An individual instance of laughter. While the algorithm is expected to detect a single individual's laugh, this
 * object represents anyone that might be involved in a detected sequence of laughter.
 *
 * Also contains flags indicating if the laughter is the result of a joke, the speaker is if so.
 * Also contains a flag indicating if the algorithm was correct.
 *
 * Created by Miles on 12/17/2016.
 */
@Entity
public class LaughInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private int s3Key;

    private long start;
    private long stop;

    private List<LaughParticipant> participants;

    private boolean joke;
    private String speaker;
    private boolean algCorrect;

    public LaughInstance(long start, long stop) {
        this.start = start;
        this.stop = stop;
    }

    public LaughInstance(long start, long stop, List<LaughParticipant> participants, boolean joke, String speaker, boolean algCorrect) {
        this.start = start;
        this.stop = stop;
        this.participants = participants;
        this.joke = joke;
        this.speaker = speaker;
        this.algCorrect = algCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStop() {
        return stop;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public List<LaughParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<LaughParticipant> participants) {
        this.participants = participants;
    }

    public boolean isJoke() {
        return joke;
    }

    public void setJoke(boolean joke) {
        this.joke = joke;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public boolean isAlgCorrect() {
        return algCorrect;
    }

    public void setAlgCorrect(boolean algCorrect) {
        this.algCorrect = algCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LaughInstance that = (LaughInstance) o;

        if (id != that.id) return false;
        if (start != that.start) return false;
        if (stop != that.stop) return false;
        if (joke != that.joke) return false;
        if (algCorrect != that.algCorrect) return false;
        if (participants != null ? !participants.equals(that.participants) : that.participants != null) return false;
        return speaker != null ? speaker.equals(that.speaker) : that.speaker == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (stop ^ (stop >>> 32));
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        result = 31 * result + (joke ? 1 : 0);
        result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
        result = 31 * result + (algCorrect ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LaughInstance{" +
                "id=" + id +
                ", start=" + start +
                ", stop=" + stop +
                ", participants=" + participants +
                ", joke=" + joke +
                ", speaker='" + speaker + '\'' +
                ", algCorrect=" + algCorrect +
                '}';
    }
}