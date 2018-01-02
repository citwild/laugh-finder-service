package edu.uw.citw.model;

import java.util.Objects;

/**
 * Model information to submit to the UI, stripping out heavy data (i.e., model binary and arff file).
 */
public class SimpleModelInfo {

    private Long id;
    private String createdDate;
    private String createdBy;
    private Boolean inUse;

    public SimpleModelInfo() {}

    public SimpleModelInfo(Long id, String createdDate, String createdBy, Boolean inUse) {
        this.id = id;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.inUse = inUse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleModelInfo that = (SimpleModelInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(inUse, that.inUse);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createdDate, createdBy, inUse);
    }

    @Override
    public String toString() {
        return "SimpleModelInfo{" +
                "id=" + id +
                ", createdDate='" + createdDate + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", inUse=" + inUse +
                '}';
    }
}
