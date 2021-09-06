package com.example.eea_part2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class ModuleDTO implements Parcelable {

    private int moduleId;
    private String moduleName;
    private String lecturer;
    private List<Batch> batchList;

    public ModuleDTO() {
    }

    protected ModuleDTO(Parcel in) {
        moduleId = in.readInt();
        moduleName = in.readString();
        lecturer = in.readString();
        batchList = in.createTypedArrayList(Batch.CREATOR);
    }

    public static final Creator<ModuleDTO> CREATOR = new Creator<ModuleDTO>() {
        @Override
        public ModuleDTO createFromParcel(Parcel in) {
            return new ModuleDTO(in);
        }

        @Override
        public ModuleDTO[] newArray(int size) {
            return new ModuleDTO[size];
        }
    };

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<Batch> batchList) {
        this.batchList = batchList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(moduleId);
        parcel.writeString(moduleName);
        parcel.writeString(lecturer);
        parcel.writeTypedList(batchList);
    }
}
