package com.domain;

import java.io.Serializable;

/**
 * 用户实体类，用于表示用户表的记录信息
 * @author : zzc
 * @version 1.1.0
 **/
public class User implements Serializable {

    private long id ;   //用户id,唯一标识符
    private String account  ; //用户账号
    private String password ; //用户密码
    private int rights ; //用户权限

    public User(){

    }

    public User(long id, String account, String password, int rights) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.rights = rights;
    }

    public User(Long teacher) {
        this.setId(teacher);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    @Override
    public String toString() {
        return "users{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", right=" + rights +
                '}';
    }
}
