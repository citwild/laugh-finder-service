package edu.uw.citw.model;

import java.util.List;
import java.util.Objects;

public class RetrainSampleFile {

    private String bucket;
    private String key;
    private List<RetrainSampleInstance> instances;

    public RetrainSampleFile() {}

    public RetrainSampleFile(String bucket, String key, List<RetrainSampleInstance> instances) {
        this.bucket = bucket;
        this.key = key;
        this.instances = instances;
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

    public List<RetrainSampleInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<RetrainSampleInstance> instances) {
        this.instances = instances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrainSampleFile that = (RetrainSampleFile) o;
        return Objects.equals(bucket, that.bucket) &&
                Objects.equals(key, that.key) &&
                Objects.equals(instances, that.instances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, key, instances);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
            "{\"key\": \"" + key + "\", " +
            "\"bucket\": \"" + bucket + "\", " +
            "\"instances\": ["
        );
        for (RetrainSampleInstance instance : instances) {
            sb.append(instance.toString()).append(",");
        }
        sb.append("]}");
        return sb.toString();
    }
}
