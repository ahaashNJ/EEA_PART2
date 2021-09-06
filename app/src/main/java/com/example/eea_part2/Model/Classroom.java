package com.example.eea_part2.Model;

public class Classroom {

    private String classroomId;
    private String floor;
    private String noOfSeats;

    public Classroom() {
    }

    public Classroom(String classroomId, String floor, String noOfSeats) {
        this.classroomId = classroomId;
        this.floor = floor;
        this.noOfSeats = noOfSeats;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(String noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    @Override
    public String toString() {
        return classroomId;
    }
}
