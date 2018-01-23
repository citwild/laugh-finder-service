package edu.uw.citw.model;

import java.util.Objects;

/**
 * Many to one relationship with RetrainSampleFile. Has timestamp and correctness.
 */
public class RetrainSampleInstance {

    private double start;
    private double stop;
    private String correct;

    // Uses Long as args because will be using database data
    public RetrainSampleInstance(Long start, Long stop, Boolean correct) {
        this.start = convertMsToSeconds(start);
        this.stop = convertMsToSeconds(stop);
        this.correct = (correct) ? "Y" : "N";
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getStop() {
        return stop;
    }

    public void setStop(double stop) {
        this.stop = stop;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrainSampleInstance that = (RetrainSampleInstance) o;
        return Double.compare(that.start, start) == 0 &&
                Double.compare(that.stop, stop) == 0 &&
                Objects.equals(correct, that.correct);
    }

    @Override
    public int hashCode() {

        return Objects.hash(start, stop, correct);
    }

    @Override
    public String toString() {
        return "{\"start\"=" + start + ",\"stop\"=" + stop + ",\"correct\"=\"" + correct + "\"" + '}';
    }

    // TODO: This should be a utility class method or something. Used in a bunch of places.
    private Double convertMsToSeconds(Long sec) {
        double result = (double) sec;
        return result / 1000;
    }
}
