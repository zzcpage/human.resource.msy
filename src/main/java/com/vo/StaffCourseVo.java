package com.vo;

import com.domain.Course;

import java.io.Serializable;

/**
 * 员工课程信息视图对象
 * @author : zzc
 * @version 1.1.0
 **/
public class StaffCourseVo implements Serializable {

    private long id ;   //课程id
    private String cname ;  //课程名称
    private String addr ;   //课程地址
    private String time ;   //开课时间
    private String intrduce ;   //课程介绍
    private String remainder ; //开课人数/剩余人数
    private String username ;

    public StaffCourseVo( ) {

    }

    public StaffCourseVo(Course course , String remainder) {
        this.id = course.getId() ;
        this.cname = course.getCname() ;
        this.addr = course.getAddr() ;
        this.time = course.getTime() ;
        this.intrduce = course.getIntrduce() ;
        this.remainder = remainder ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRemainder() {
        return remainder;
    }

    public void setRemainder(String remainder) {
        this.remainder = remainder;
    }
}
