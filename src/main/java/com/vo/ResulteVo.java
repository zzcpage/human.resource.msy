package com.vo;

import java.io.Serializable;

/**
 * 员工成绩视图对象
 * @author : zzc
 * @version 1.1.0
 **/
public class ResulteVo implements Serializable {

    private long id;   //用户唯一标识符
    private String username ;  //员工姓名
    private String birthday ;   // 员工生日
    private String sex;      //  员工性别
    private String phone ;  //   员工电话号码
    private String department ; //员工部门
    private double grade ; //分数

    public ResulteVo(){}

    public ResulteVo(long id, String username, String birthday, String sex, String phone, String department, double grade) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.grade = grade;
    }

    public ResulteVo(String username, String birthday, String sex, String phone, String department, double grade) {
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ResulteBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                ", grade=" + grade +
                '}';
    }
}
