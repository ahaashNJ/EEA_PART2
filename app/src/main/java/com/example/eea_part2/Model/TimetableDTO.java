package com.example.eea_part2.Model;

import java.util.List;

public class TimetableDTO {

    private int timetableId;
    private String startTime;
    private String endTime;
    private String timetableDate;
    private String classroom;
    private String module;
    List<Batch> batchList;

    public TimetableDTO() {
    }

    public TimetableDTO(int timetableId, String startTime, String endTime, String timetableDate, String classroom, String module, List<Batch> batchList) {
        this.timetableId = timetableId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timetableDate = timetableDate;
        this.classroom = classroom;
        this.module = module;
        this.batchList = batchList;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimetableDate() {
        return timetableDate;
    }

    public void setTimetableDate(String timetableDate) {
        this.timetableDate = timetableDate;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<Batch> batchList) {
        this.batchList = batchList;
    }
}
