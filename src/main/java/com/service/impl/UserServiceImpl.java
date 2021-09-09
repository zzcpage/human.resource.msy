package com.service.impl;

import com.dao.UserDao;
import com.domain.User;
import com.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.UserService;

import java.util.List;

/**
 * 用于提供用户业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.UserService
 **/
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao ;

    @Override
    public User UserLogin(String account, String password) {
        System.out.println("进入userService");
        System.out.println(account);
        System.out.println(password);
        return userDao.findUserByAccountAndPassword(account,password);
    }

    @Override
    public boolean insertUser(User user,String phone,int right) {
        try{
            user.setPassword(MD5Util.getEncryptedPwd("111111"));
            user.setRights(right);
            user.setAccount(phone);
            return userDao.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDao.deleteUser(user.getId());
    }

    @Override
    public User findUser(String phone) {
        return userDao.findUserByAccount(phone);
    }

    @Override
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }
}
