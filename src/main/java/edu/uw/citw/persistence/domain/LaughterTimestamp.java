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
@Table(name = "laugh_instance", schema = "uwbwfe")
public class LaughterTimestamp implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "S3_KEY", nullable = false)
    private Long s3Key;

    @Column(name = "VID_START", nullable = false)
    private String startTime;

    @Column(name = "VID_STOP", nullable = false)
    private Long stopTime;

    public LaughterTimestamp() {}

    public LaughterTimestamp(Long id, Long s3Key, String startTime, Long stopTime) {
        this.id = id;
        this.s3Key = s3Key;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getS3Key() {
        return s3Key;
    }

    public void setS3Key(Long s3Key) {
        this.s3Key = s3Key;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LaughterTimestamp that = (LaughterTimestamp) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (s3Key != null ? !s3Key.equals(that.s3Key) : that.s3Key != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        return stopTime != null ? stopTime.equals(that.stopTime) : that.stopTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (s3Key != null ? s3Key.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (stopTime != null ? stopTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LaughterTimestamp{" +
                "id=" + id +
                ", s3Key=" + s3Key +
                ", startTime='" + startTime + '\'' +
                ", stopTime=" + stopTime +
                '}';
    }
}
