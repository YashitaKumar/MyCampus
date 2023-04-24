package com.example.mycampus.Models;

public class ClubHeadModel {
    public ClubHeadModel(int enrollmentNo, String club) {
        this.enrollmentNo = enrollmentNo;
        this.club = club;
    }

    public int getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(int enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    int enrollmentNo;
    String club;
}
