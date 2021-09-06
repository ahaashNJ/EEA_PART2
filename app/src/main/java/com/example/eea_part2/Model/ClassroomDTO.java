package com.example.eea_part2.Model;

public class ClassroomDTO {

    private String classroomID;
    private String floorNum;
    private String numOfSeats;

    public ClassroomDTO(String classroomID, String floorNum, String numOfSeats) {
        this.classroomID = classroomID;
        this.floorNum = floorNum;
        this.numOfSeats = numOfSeats;
    }

    public ClassroomDTO() {
    }

    public String getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(String classroomID) {
        this.classroomID = classroomID;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(String numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    @Override
    public String toString() {
        return classroomID;
    }
}
