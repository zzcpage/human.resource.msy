package com.vo;

import com.domain.Course;
import com.domain.Pay;

import java.io.Serializable;

/**
 * 课程视图对象，用于提供页面所需的数据
 * @author : zzc
 * @version 1.1.0
 **/
public class CourseVo implements Serializable {

    private long id;  //课程id
    private String cname;  //课程名称
    private String addr;   //开课地点
    private String time;   //开课时间
    private String intrduce;   //课程介绍
    private int num;       //开课人数
    private long phone;    //教师电话
    private double qualified;  //合格员工薪资/人
    private double basic; //底薪
    private int peopleNum; //选课人数
    private int qualifiedNum;//及格人数
    private double pay;//薪资

    public CourseVo() {

    }

    public CourseVo(Course course, long phone, Pay pay) {
        this.id = course.getId();
        this.cname = course.getCname();
        this.addr = course.getAddr();
        this.time = course.getTime();
        this.intrduce = course.getIntrduce();
        this.num = course.getNum();
        this.phone = phone;
        this.qualified = pay.getQualified();
        this.basic = pay.getBasic();
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(int qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public double getQualified() {
        return qualified;
    }

    public void setQualified(double qualified) {
        this.qualified = qualified;
    }

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

    @Override
    public String toString() {
        return "CourseVo{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", addr='" + addr + '\'' +
                ", time='" + time + '\'' +
                ", intrduce='" + intrduce + '\'' +
                ", num=" + num +
                ", phone=" + phone +
                ", qualified=" + qualified +
                ", basic=" + basic +
                ", peopleNum=" + peopleNum +
                ", qualifiedNum=" + qualifiedNum +
                ", pay=" + pay +
                '}';
    }
}
