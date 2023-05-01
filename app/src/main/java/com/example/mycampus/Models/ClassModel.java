package com.example.mycampus.Models;

public class ClassModel {
    String classId,Subject,teacherName,teacherId,batch;
    int total,held;

    public ClassModel() {
    }

    public ClassModel(String classId, String subject, String teacherName, String teacherId, String batch, int total, int held) {
        this.classId = classId;
        Subject = subject;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.batch = batch;
        this.total = total;
        this.held = held;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getHeld() {
        return held;
    }

    public void setHeld(int held) {
        this.held = held;
    }
}
