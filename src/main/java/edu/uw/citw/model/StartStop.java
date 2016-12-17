package edu.uw.citw.model;

/**
 * Starting and stoping timestamps
 *
 * Created by Miles on 12/17/2016.
 */
public class StartStop {

    private long start;
    private long stop;

    public StartStop(long start, long stop) {
        this.start = start;
        this.stop = stop;
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
}
