package com.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * 教师实体类，用于表示教师表中的记录信息
 * @author : zzc
 * @version 1.1.0
 **/
public class Teacher implements Serializable {

    private long id;   //用户唯一标识符
    private String username ;  //教师姓名
    private String birthday ;   // 教师生日
    private String sex;      //  教师性别
    private String phone ;  //   教师电话号码

    public Teacher(){}

    public Teacher(String username, String birthday, String sex, String phone) {
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
    }

    public Teacher(long id, String username, String birthday, String sex, String phone) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
    }

    public Teacher(Long teacher) {
        this.id = teacher ;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
