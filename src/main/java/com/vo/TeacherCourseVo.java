package com.vo;

import com.domain.Course;

/**
 * 教师课程信息视图对象
 * @author : zzc
 * @version 1.1.0
 **/
public class TeacherCourseVo {
    private long id ;   //课程id
    private String cname ;  //课程名称
    private String addr ;   //课程地址
    private String time ;   //开课时间
    private String intrduce ;   //课程介绍
    private String remainder ; //开课人数/剩余人数
    private int num ; //开课人数
    private double qualified ;//合格薪资
    private int peopleNum ; //选课人数
    private int qualifiedNum ;//及格人数
    private double basic ; //底薪
    private double pay ;//薪资

    public TeacherCourseVo( ) {
    }

    public TeacherCourseVo(Course course , String remainder) {
        this.id = course.getId() ;
        this.cname = course.getCname() ;
        this.addr = course.getAddr() ;
        this.time = course.getTime() ;
        this.intrduce = course.getIntrduce() ;
        this.remainder = remainder ;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getQualified() {
        return qualified;
    }

    public void setQualified(double qualified) {
        this.qualified = qualified;
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

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

    @Override
    public String toString() {
        return "TeacherCourseVo{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", addr='" + addr + '\'' +
                ", time='" + time + '\'' +
                ", intrduce='" + intrduce + '\'' +
                ", remainder='" + remainder + '\'' +
                ", num=" + num +
                ", qualified=" + qualified +
                ", peopleNum=" + peopleNum +
                ", qualifiedNum=" + qualifiedNum +
                ", basic=" + basic +
                ", pay=" + pay +
                '}';
    }
}
