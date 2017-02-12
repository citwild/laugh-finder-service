package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a row within the SAVED_LAUGHTER_TIMESTAMPS table.
 *
 * Created by Miles on 9/25/2016.
 */
@Entity
@Table(name = "laugh_instance", schema = "uwbwfe")
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

    @Column(name = "IS_JOKE", nullable = false)
    private Boolean joke;

    @Column(name = "JOKE_SPEAKER", nullable = true)
    private String jokeSpeaker;

    @Column(name = "ALG_CORRECT", nullable = false)
    private Boolean algCorrect;

    public LaughterInstance() {}

    public LaughterInstance(Long id, Long s3Key, Long startTime, Long stopTime) {
        this.id = id;
        this.s3Key = s3Key;
        this.startTime = startTime;
        this.stopTime = stopTime;

        this.joke = false;
        this.algCorrect = true;
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

    public Boolean getJoke() {
        return joke;
    }

    public void setJoke(Boolean joke) {
        this.joke = joke;
    }

    public String getJokeSpeaker() {
        return jokeSpeaker;
    }

    public void setJokeSpeaker(String jokeSpeaker) {
        this.jokeSpeaker = jokeSpeaker;
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

        LaughterInstance that = (LaughterInstance) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (s3Key != null ? !s3Key.equals(that.s3Key) : that.s3Key != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (stopTime != null ? !stopTime.equals(that.stopTime) : that.stopTime != null) return false;
        if (joke != null ? !joke.equals(that.joke) : that.joke != null) return false;
        if (jokeSpeaker != null ? !jokeSpeaker.equals(that.jokeSpeaker) : that.jokeSpeaker != null) return false;
        return algCorrect != null ? algCorrect.equals(that.algCorrect) : that.algCorrect == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (s3Key != null ? s3Key.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (stopTime != null ? stopTime.hashCode() : 0);
        result = 31 * result + (joke != null ? joke.hashCode() : 0);
        result = 31 * result + (jokeSpeaker != null ? jokeSpeaker.hashCode() : 0);
        result = 31 * result + (algCorrect != null ? algCorrect.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LaughterInstance{" +
                "id=" + id +
                ", s3Key=" + s3Key +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", joke=" + joke +
                ", jokeSpeaker='" + jokeSpeaker + '\'' +
                ", algCorrect=" + algCorrect +
                '}';
    }
}
