package com.jayant.pocketlibrary.user;

public class UserData {

    String name, email, sem, college, branch, date, loc;

    public UserData() {
    }

    public UserData(String name, String email, String sem, String college, String branch, String date, String loc) {
        this.name = name;
        this.email = email;
        this.sem = sem;
        this.college = college;
        this.branch = branch;
        this.date = date;
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", sem='" + sem + '\'' +
                ", college='" + college + '\'' +
                ", branch='" + branch + '\'' +
                ", date='" + date + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
