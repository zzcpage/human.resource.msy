package com.vo;

import java.io.Serializable;

/**
 * 员工课程成绩视图对象
 * @author : zzc
 * @version 1.1.0
 **/
public class StaffPageCourseVo implements Serializable {

    private long id;   //课程id
    private String cname;  //课程名称
    private String addr;   //课程地址
    private String time;   //开课时间
    private String intrduce;   //课程介绍
    private String username;   //教师名称
    private String grade;      //课程成绩

    public StaffPageCourseVo() {

    }

    public StaffPageCourseVo(long id, String cname, String addr, String time, String intrduce, String username, String grade) {
        this.id = id;
        this.cname = cname;
        this.addr = addr;
        this.time = time;
        this.intrduce = intrduce;
        this.username = username;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntrduce() {
        return intrduce;
    }

    public void setIntrduce(String intrduce) {
        this.intrduce = intrduce;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StaffPageCourseBean{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", addr='" + addr + '\'' +
                ", time='" + time + '\'' +
                ", intrduce='" + intrduce + '\'' +
                ", username='" + username + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
