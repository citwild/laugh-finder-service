package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "laugh_instance_tags", schema = "dbo")
public class Tag implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INSTANCE_ID", nullable = false)
    private Long instanceId;

    @Column(name = "TAG_ID", nullable = false)
    private Long tagId;

    public Tag() {}

    public Tag(Long instanceId, Long tagId) {
        this.instanceId = instanceId;
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(instanceId, tag.instanceId) &&
                Objects.equals(tagId, tag.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instanceId, tagId);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", instanceId=" + instanceId +
                ", tagId=" + tagId +
                '}';
    }
}
