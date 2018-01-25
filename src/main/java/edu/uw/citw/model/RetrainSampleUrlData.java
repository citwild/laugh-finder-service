package edu.uw.citw.model;

import java.util.Objects;

public class RetrainSampleUrlData {

    private String bucket;
    private String key;
    private Double startTime;
    private Double stopTime;
    private Boolean algCorrect;

    public RetrainSampleUrlData(String bucket, String key, Double startTime, Double stopTime, Boolean algCorrect) {
        this.bucket = bucket;
        this.key = key;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.algCorrect = algCorrect;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public Double getStopTime() {
        return stopTime;
    }

    public void setStopTime(Double stopTime) {
        this.stopTime = stopTime;
    }

    public Boolean getAlgCorrect() {
        return algCorrect;
    }

    public void setAlgCorrect(Boolean algCorrect) {
        this.algCorrect = algCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrainSampleUrlData that = (RetrainSampleUrlData) o;
        return Objects.equals(bucket, that.bucket) &&
                Objects.equals(key, that.key) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(stopTime, that.stopTime) &&
                Objects.equals(algCorrect, that.algCorrect);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bucket, key, startTime, stopTime, algCorrect);
    }

    @Override
    public String toString() {
        return "RetrainSampleUrlData{" +
                "bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", algCorrect=" + algCorrect +
                '}';
    }
}
