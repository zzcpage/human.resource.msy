package com.domain;

import java.io.Serializable;

/**
 * 成绩记录表，用于表示数据库的成绩记录
 * @author : zzc
 * @version 1.1.0
 **/
public class Resulte implements Serializable {

    private long id ;   //成绩记录Id
    private long uid ;  //员工id
    private long cid ;  //课程id
    private double grade ; //课程成绩

    public Resulte() {}

    public Resulte(long uid, long cid) {
        this.uid = uid;
        this.cid = cid;
    }

    public Resulte(long uid, long cid, double grade) {
        this.uid = uid;
        this.cid = cid;
        this.grade = grade;
    }

    public Resulte(long id, long uid, long cid, double grade) {
        this.id = id;
        this.uid = uid;
        this.cid = cid;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", uid=" + uid +
                ", cid=" + cid +
                ", grade=" + grade +
                '}';
    }
}
