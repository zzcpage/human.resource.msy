package com.domain;

import java.io.Serializable;

/**
 * 授课记录实体，用于表示教师的授课记录
 * @author : zzc
 * @version 1.1.0
 **/
public class Lecture implements Serializable {

    private long id ;   //记录id
    private long tid ;  //教师id
    private long cid ;  //课程id

    public Lecture() {}

    public Lecture(long id, long tid, long cid) {
        this.id = id;
        this.tid = tid;
        this.cid = cid;
    }

    public Lecture(long tid, long cid) {
        this.tid = tid;
        this.cid = cid;
    }

    public Lecture(long id) {
        this.cid = id ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", tid=" + tid +
                ", cid=" + cid +
                '}';
    }
}
