package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "model_data", schema = "dbo")
public class ModelData implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "MODEL_BINARY", nullable = false)
    private byte[] modelBinary;

    @Column(name = "ARFF_DATA", nullable = false)
    private String arffData;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @Column(name = "CURRENTLY_IN_USE", nullable = false)
    private boolean inUse;


    public ModelData() {}

    public long getId() {
        return id;
    }

    public byte[] getModelBinary() {
        return modelBinary;
    }

    public void setModelBinary(byte[] modelBinary) {
        this.modelBinary = modelBinary;
    }

    public String getArffData() {
        return arffData;
    }

    public void setArffData(String arffData) {
        this.arffData = arffData;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelData modelData = (ModelData) o;
        return id == modelData.id &&
                inUse == modelData.inUse &&
                Arrays.equals(modelBinary, modelData.modelBinary) &&
                Objects.equals(arffData, modelData.arffData) &&
                Objects.equals(createdDate, modelData.createdDate) &&
                Objects.equals(createdBy, modelData.createdBy);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, arffData, createdDate, createdBy, inUse);
        result = 31 * result + Arrays.hashCode(modelBinary);
        return result;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "id=" + id +
                ", modelBinary=" + Arrays.toString(modelBinary) +
                ", arffData='" + arffData + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", inUse=" + inUse +
                '}';
    }
}
