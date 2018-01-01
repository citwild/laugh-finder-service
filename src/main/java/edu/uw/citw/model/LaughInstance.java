package edu.uw.citw.model;

import edu.uw.citw.persistence.domain.LaughterInstance;

import java.util.Objects;

/**
 * An individual instance of laughter. While the algorithm is expected to detect a single individual's laugh, this
 * object represents anyone that might be involved in a detected sequence of laughter.
 *
 * Also contains a flag indicating if the algorithm was correct.
 *
 * Created by Miles on 12/17/2016.
 */
public class LaughInstance {

    private long id;
    private long start;
    private long stop;
    private boolean algCorrect;
    private boolean userMade;
    private boolean retrain;

    public LaughInstance(long start, long stop) {
        this.start = start;
        this.stop = stop;
    }

    public LaughInstance(long start, long stop, boolean algCorrect, boolean userMade, boolean retrain) {
        this.start = start;
        this.stop = stop;
        this.algCorrect = algCorrect;
        this.userMade = userMade;
        this.retrain = retrain;
    }

    public LaughInstance(LaughterInstance instance) {
        this.id = instance.getId();
        this.start = instance.getStartTime();
        this.stop = instance.getStopTime();
        this.algCorrect = instance.getAlgCorrect();
        this.userMade = instance.getUserMade();
        this.retrain = instance.getUseForRetrain();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isAlgCorrect() {
        return algCorrect;
    }

    public void setAlgCorrect(boolean algCorrect) {
        this.algCorrect = algCorrect;
    }

    public boolean isUserMade() {
        return userMade;
    }

    public void setUserMade(boolean userMade) {
        this.userMade = userMade;
    }

    public boolean isRetrain() {
        return retrain;
    }

    public void setRetrain(boolean retrain) {
        this.retrain = retrain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaughInstance that = (LaughInstance) o;
        return id == that.id &&
                start == that.start &&
                stop == that.stop &&
                algCorrect == that.algCorrect &&
                userMade == that.userMade &&
                retrain == that.retrain;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, start, stop, algCorrect, userMade, retrain);
    }

    @Override
    public String toString() {
        return "LaughInstance{" +
                "id=" + id +
                ", start=" + start +
                ", stop=" + stop +
                ", algCorrect=" + algCorrect +
                ", userMade=" + userMade +
                ", retrain=" + retrain +
                '}';
    }
}
