package com.example.app.model;

public class ClassroomDTO {
    private String idRoom;
    private String name;
    private int capacity; // Trường sức chứa

    public ClassroomDTO(String idRoom, String name, int capacity) { // Cập nhật constructor
        this.idRoom = idRoom;
        this.name = name;
        this.capacity = capacity; // Gán sức chứa
    }

    public String getIdRoom() {
        return idRoom;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() { // Thêm phương thức getter cho sức chứa
        return capacity;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) { // Thêm phương thức setter cho sức chứa
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "ClassroomDTO{" +
                "idRoom='" + idRoom + '\'' +
                ", name='" + name + '\'' +
                ", capacity=" + capacity + // Cập nhật phương thức toString
                '}';
    }
}