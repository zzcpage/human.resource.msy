package com.domain;

import java.io.Serializable;

/**
 * 课程实体类，用于表示数据库中课程表
 * @author : zzc
 * @version 1.1.0
 **/
public class Course implements Serializable {

    private long id ;   //课程唯一标识符
    private String cname ;  //课程名称
    private String addr ;   //上课地点
    private String time ;   //上课时间
    private String intrduce ;   //课程介绍
    private int num ;   //开课人数

    public Course(){};
    public Course(Long id){this.id = id ; };
    public Course(String cname, String addr, String time, String intrduce, int num) {
        this.cname = cname;
        this.addr = addr;
        this.time = time;
        this.intrduce = intrduce;
        this.num = num;
    }

    public Course(long id, String cname, String addr, String time, String intrduce, int num) {
        this.id = id;
        this.cname = cname;
        this.addr = addr;
        this.time = time;
        this.intrduce = intrduce;
        this.num = num;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", addr='" + addr + '\'' +
                ", time='" + time + '\'' +
                ", intrduce='" + intrduce + '\'' +
                ", num=" + num +
                '}';
    }
}
