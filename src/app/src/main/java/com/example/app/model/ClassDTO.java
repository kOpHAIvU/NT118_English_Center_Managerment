package com.example.app.model;

public class ClassDTO {
    private int idStatus;
    private String classID, className, startDate, endDate, idProgram, idTeacher, idStaff, status;
    private String statusDisplay; // Trạng thái hiển thị
    private String colorResId; // Mã màu trạng thái

    // Constructor
    public ClassDTO(String classID, String className, String startDate,
                    String endDate, String idProgram, String idTeacher,
                    String idStaff, String status, String colorResId) {
        this.classID = classID;
        this.className = className;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idProgram = idProgram;
        this.idTeacher = idTeacher;
        this.idStaff = idStaff;
        this.status = status;
        this.colorResId = colorResId; // Khởi tạo mã màu trạng thái
    }

    // Getter và Setter cho colorResId
    public String getColorResId() {
        return colorResId;
    }

    public void setColorResId(String colorResId) {
        this.colorResId = colorResId;
    }

    // Getter và Setter cho các thuộc tính khác
    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(String idProgram) {
        this.idProgram = idProgram;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    @Override
    public String toString() {
        return "ClassDTO{" +
                "classID='" + classID + '\'' +
                ", className='" + className + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", idProgram='" + idProgram + '\'' +
                ", idTeacher='" + idTeacher + '\'' +
                ", idStaff='" + idStaff + '\'' +
                ", status='" + status + '\'' +
                ", colorResId='" + colorResId + '\'' + // Thêm colorResId vào toString
                '}';
    }
}