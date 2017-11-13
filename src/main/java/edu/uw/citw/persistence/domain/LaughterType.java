package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A row from the LAUGH_TYPES table.
 *
 * Created by miles on 2/11/17.
 */
@Entity
@Table(name = "laugh_types", schema = "dbo")
public class LaughterType implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "CONSIDERED", nullable = false)
    private Boolean considered;

    public LaughterType() {}

    public LaughterType(Long id, String type, String description, Boolean considered) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.considered = considered;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getConsidered() {
        return considered;
    }

    public void setConsidered(Boolean considered) {
        this.considered = considered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LaughterType that = (LaughterType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return considered != null ? considered.equals(that.considered) : that.considered == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (considered != null ? considered.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LaughterType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", considered=" + considered +
                '}';
    }
}
