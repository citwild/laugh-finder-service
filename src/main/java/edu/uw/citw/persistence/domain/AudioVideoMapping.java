package edu.uw.citw.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represents a mapping between one audio resource and one video resource. For identifying
 * and audio file extracted from a video file.
 *
 * Created by Miles on 9/17/2016.
 */
@Entity
@Table(name = "transcoder_vid_aud_mappings", schema = "dbo")
public class AudioVideoMapping implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "BUCKET", nullable = false)
    private String bucket;

    @Column(name = "MP4_FILE", nullable = false)
    private String videoFile;

    @Column(name = "WAV_FILE", nullable = false)
    private String audioFile;

    public AudioVideoMapping() {}

    public AudioVideoMapping(String bucket, String videoFile, String audioFile) {
        this.bucket = bucket;
        this.videoFile = videoFile;
        this.audioFile = audioFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudioVideoMapping that = (AudioVideoMapping) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (bucket != null ? !bucket.equals(that.bucket) : that.bucket != null) return false;
        if (videoFile != null ? !videoFile.equals(that.videoFile) : that.videoFile != null) return false;
        return audioFile != null ? audioFile.equals(that.audioFile) : that.audioFile == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bucket != null ? bucket.hashCode() : 0);
        result = 31 * result + (videoFile != null ? videoFile.hashCode() : 0);
        result = 31 * result + (audioFile != null ? audioFile.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AudioVideoMapping{" +
                "id=" + id +
                ", bucket='" + bucket + '\'' +
                ", videoFile='" + videoFile + '\'' +
                ", audioFile='" + audioFile + '\'' +
                '}';
    }
}
