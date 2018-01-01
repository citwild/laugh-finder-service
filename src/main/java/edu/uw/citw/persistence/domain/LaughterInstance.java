package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a row within the SAVED_LAUGHTER_TIMESTAMPS table.
 *
 * Created by Miles on 9/25/2016.
 */
@Entity
@Table(name = "laugh_instance", schema = "dbo")
public class LaughterInstance implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "S3_KEY", nullable = false)
    private Long s3Key;

    @Column(name = "VID_START", nullable = false)
    private Long startTime;

    @Column(name = "VID_STOP", nullable = false)
    private Long stopTime;

    @Column(name = "ALG_CORRECT", nullable = false)
    private Boolean algCorrect;

    @Column(name = "USER_MADE", nullable = false)
    private Boolean userMade;

    @Column(name = "USE_FOR_RETRAIN", nullable = false)
    private Boolean useForRetrain;

    public LaughterInstance() {}

    public LaughterInstance(Long id, Long s3Key, Long startTime, Long stopTime) {
        this.id = id;
        this.s3Key = s3Key;
        this.startTime = startTime;
        this.stopTime = stopTime;

        this.algCorrect = true;
        this.userMade = false;
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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    public Boolean getAlgCorrect() {
        return algCorrect;
    }

    public void setAlgCorrect(Boolean algCorrect) {
        this.algCorrect = algCorrect;
    }

    public Boolean getUserMade() {
        return userMade;
    }

    public void setUserMade(Boolean userMade) {
        this.userMade = userMade;
    }

    public Boolean getUseForRetrain() {
        return useForRetrain;
    }

    public void setUseForRetrain(Boolean useForRetrain) {
        this.useForRetrain = useForRetrain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaughterInstance that = (LaughterInstance) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(s3Key, that.s3Key) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(stopTime, that.stopTime) &&
                Objects.equals(algCorrect, that.algCorrect) &&
                Objects.equals(userMade, that.userMade) &&
                Objects.equals(useForRetrain, that.useForRetrain);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, s3Key, startTime, stopTime, algCorrect, userMade, useForRetrain);
    }

    @Override
    public String toString() {
        return "LaughterInstance{" +
                "id=" + id +
                ", s3Key=" + s3Key +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", algCorrect=" + algCorrect +
                ", userMade=" + userMade +
                ", useForRetrain=" + useForRetrain +
                '}';
    }
}
