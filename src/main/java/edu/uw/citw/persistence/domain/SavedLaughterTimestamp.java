package edu.uw.citw.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represents a row within the SAVED_LAUGHTER_TIMESTAMPS table.
 *
 * Created by Miles on 9/25/2016.
 */
@Entity
@Table(name = "saved_laughter_timestamps", schema = "uwbwfe")
public class SavedLaughterTimestamp implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "S3_BUCKET", nullable = false)
    private String s3Bucket;

    @Column(name = "S3_KEY", nullable = false)
    private String s3Key;

    @Column(name = "TIMESTAMP_JSON", nullable = false)
    private String timestampJson;

    public SavedLaughterTimestamp() {}

    public SavedLaughterTimestamp(String s3Bucket, String s3Key, String timestampJson) {
        this.s3Bucket = s3Bucket;
        this.s3Key = s3Key;
        this.timestampJson = timestampJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String getTimestampJson() {
        return timestampJson;
    }

    public void setTimestampJson(String timestampJson) {
        this.timestampJson = timestampJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedLaughterTimestamp that = (SavedLaughterTimestamp) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (s3Bucket != null ? !s3Bucket.equals(that.s3Bucket) : that.s3Bucket != null) return false;
        if (s3Key != null ? !s3Key.equals(that.s3Key) : that.s3Key != null) return false;
        return timestampJson != null ? timestampJson.equals(that.timestampJson) : that.timestampJson == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (s3Bucket != null ? s3Bucket.hashCode() : 0);
        result = 31 * result + (s3Key != null ? s3Key.hashCode() : 0);
        result = 31 * result + (timestampJson != null ? timestampJson.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SavedLaughterTimestamp{" +
                "id=" + id +
                ", s3Bucket='" + s3Bucket + '\'' +
                ", s3Key='" + s3Key + '\'' +
                ", timestampJson='" + timestampJson + '\'' +
                '}';
    }
}
