package com.vo;

import com.domain.Teacher;

/**
 * 教师薪资视图对象
 * @author : zzc
 * @version 1.1.0
 **/
public class TeacherPayVo {
    private long id;             // 用户唯一标识符
    private String username ;   // 教师姓名
    private String birthday ;  // 教师生日
    private String sex;       // 教师性别
    private String phone ;   // 教师电话号码
    private double pay ;    // 教师薪资

    public TeacherPayVo() {
    }
    public TeacherPayVo(Teacher teacher , double pay){
        this.id = teacher.getId();
        this.username = teacher.getUsername();
        this.birthday = teacher.getBirthday();
        this.sex = teacher.getSex();
        this.phone = teacher.getPhone();
        this.pay = pay ;
    }
    public TeacherPayVo(String username, String birthday, String sex, String phone, double pay) {
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.pay = pay;
    }

    public TeacherPayVo(long id, String username, String birthday, String sex, String phone, double pay) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.pay = pay;
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

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "TeacherPayVo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", pay=" + pay +
                '}';
    }
}
