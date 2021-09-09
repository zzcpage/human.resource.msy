package com.domain;

import java.io.Serializable;

/**
 * 选课记录实体类，用于存放选课记录信息
 * @author : zzc
 * @version 1.1.0
 **/
public class Record implements Serializable {
    private long id ;   //记录id
    private long uid ;  //选课成员id
    private long cid ;  //课程id
    private int state;  //成绩状态 0为未录入，1为已录入
    public Record(){}

    public Record(long uid , long cid){
        this.uid = uid ;
        this.cid = cid ;
    }

    public Record(long id, long uid, long cid) {
        this.id = id;
        this.uid = uid;
        this.cid = cid;
    }

    public Record(long id, long uid, long cid, int state) {
        this.id = id;
        this.uid = uid;
        this.cid = cid;
        this.state = state;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", uid=" + uid +
                ", cid=" + cid +
                ", state=" + state +
                '}';
    }
}
