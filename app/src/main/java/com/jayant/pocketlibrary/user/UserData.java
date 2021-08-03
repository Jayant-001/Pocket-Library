package com.jayant.pocketlibrary.user;

public class UserData {

    String name, enroll, course, college, sem, date;

    public UserData() {
    }

    public UserData(String name, String enroll, String course, String college, String sem, String date) {
        this.name = name;
        this.enroll = enroll;
        this.course = course;
        this.college = college;
        this.sem = sem;
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", enroll='" + enroll + '\'' +
                ", course='" + course + '\'' +
                ", college='" + college + '\'' +
                ", sem='" + sem + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
