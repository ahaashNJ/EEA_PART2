package com.example.eea_part2.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Batch implements Parcelable {


    private Integer batchId;
    private String batchName;
    private String startDate;
    private String endDate;


    public Batch() {
    }

    protected Batch(Parcel in) {
        if (in.readByte() == 0) {
            batchId = null;
        } else {
            batchId = in.readInt();
        }
        batchName = in.readString();
        startDate = in.readString();
        endDate = in.readString();
    }

    public static final Creator<Batch> CREATOR = new Creator<Batch>() {
        @Override
        public Batch createFromParcel(Parcel in) {
            return new Batch(in);
        }

        @Override
        public Batch[] newArray(int size) {
            return new Batch[size];
        }
    };

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Batch(Integer batchId, String batchName, String startDate, String endDate) {
        this.batchId = batchId;
        this.batchName = batchName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return batchName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (batchId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(batchId);
        }
        parcel.writeString(batchName);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
    }
}
