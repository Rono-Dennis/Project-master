package com.example.realtimeschedule.model;

public class Services {
    private String sname, image, pid, date, time;

    public Services() {
    }

    public Services(String sname, String image, String pid, String date, String time) {
        this.sname = sname;
        this.image = image;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Services{" +
                "sname='" + sname + '\'' +
                ", image='" + image + '\'' +
                ", pid='" + pid + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
