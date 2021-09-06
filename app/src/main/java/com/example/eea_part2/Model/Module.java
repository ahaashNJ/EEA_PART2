package com.example.eea_part2.Model;

public class Module {


    private int moduleId;
    private String moduleName;
    private String lecturer;

    private String[] batchList;

    public Module(int moduleId, String moduleName, String lecturer, String[] batchList) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.lecturer = lecturer;
        this.batchList = batchList;
    }

    public Module() {
    }

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

    public String[] getBatchList() {
        return batchList;
    }

    public void setBatchList(String[] batchList) {
        this.batchList = batchList;
    }
}
