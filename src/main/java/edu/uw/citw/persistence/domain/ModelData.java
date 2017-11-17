package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

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
    private byte[] arffData;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;


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

    public byte[] getArffData() {
        return arffData;
    }

    public void setArffData(byte[] arffData) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelData modelData = (ModelData) o;

        if (id != modelData.id) return false;
        if (!Arrays.equals(modelBinary, modelData.modelBinary)) return false;
        if (!Arrays.equals(arffData, modelData.arffData)) return false;
        if (createdDate != null ? !createdDate.equals(modelData.createdDate) : modelData.createdDate != null)
            return false;
        return createdBy != null ? createdBy.equals(modelData.createdBy) : modelData.createdBy == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + Arrays.hashCode(modelBinary);
        result = 31 * result + Arrays.hashCode(arffData);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "id=" + id +
                ", modelBinary=" + Arrays.toString(modelBinary) +
                ", arffData=" + Arrays.toString(arffData) +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
