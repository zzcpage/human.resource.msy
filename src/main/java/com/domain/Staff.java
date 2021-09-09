package com.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 * 员工类，用于表示数据库员工表记录
 * @author : zzc
 * @version 1.1.0
 **/
public class Staff implements Serializable {

     private long id;   //用户唯一标识符
     private String username ;  //员工姓名
     private String birthday ;   // 员工生日
     private String sex;      //  员工性别
     private String phone ;  //   员工电话号码
     private String department ; //员工部门

    public Staff(){}

    public Staff(String username, String birthday, String sex, String phone, String department) {
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
    }

    public Staff(long id, String username, String birthday, String sex, String phone, String department) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
    }

    public Staff(long lst) {
        this.id = lst;
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

    public void changeDate(){

    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
