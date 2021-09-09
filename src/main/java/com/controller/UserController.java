package com.controller;

import com.domain.Staff;
import com.domain.Teacher;
import com.service.StaffService;
import com.service.TeacherService;
import com.service.UserService;
import com.domain.User;
import com.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;


/**
 * 用于用户模块的前后端交互
 * @author : zzc
 * @version 1.1.0
 **/
@Controller("userController")
@RequestMapping(value = "/user")
@SessionAttributes(value = {"id"})
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService ;
    @Autowired
    private TeacherService teacherService;

    /**
     * 进行用户登录
     * <p><pre>{@code
     * 访问url: /user/login
     * 访问方式: {GET,POST}
     * 请求参数：account,password
     * 例子：
     * 通过GET方式访问：/user/login?account=1&password=2
     * }</pre></p>
     * @param account   用户账号
     * @param password  用户密码
     * @param model 用于返回给页面数据
     * @return  跳转到指定页面
     */
    @RequestMapping(value = "/login")
    public String login(@RequestParam("account") String account , @RequestParam("password") String password, Model model){
        //1. 根据用户账号查询用户信息
        User user = userService.findUser(account) ;

        //2. 判断是否存在该用户，通过MD5验证密码是否一致
        if(user == null || !MD5Util.validPassword(password,user.getPassword())){
            return "login_fail" ;   // 不存在用户或者密码错误跳转到错误页面
        } else{
            if(user.getRights() == 0 ){ //管理员用户，跳转到管理员页面
                return "admin";
            }else if(user.getRights() == 1){ //教师用户，跳转到指定页面
                model.addAttribute("id",user.getId()) ; //向页面传送用户id
                return "teacher" ;
            }else{  //普通员工页面
                model.addAttribute("id",user.getId()) ; //向页面传送用户id
                return "human" ;
            }
        }
    }

    /**
     * 增加新用户，新员工,分配新的账号
     * <p><pre>{@code
     * 访问url: /user/addHuman
     * 访问方式: {GET,POST}
     * 请求参数：username,sex,birthday,phone,department
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap ,如果 hashmap.get("state") ==1 则增加成功，hashmap.get("state") == 0 则增加失败
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping(value = "/addHuman")
    public HashMap addHuman(HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        // 1.获取员工基本信息
        HashMap s = new HashMap() ;
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String sex = request.getParameter("sex") ;
        String birthdays = request.getParameter("birthday") ;
        String phone = request.getParameter("phone") ;
        String department = request.getParameter("department") ;
        Staff staff = new Staff(username,birthdays, sex,phone,department) ;

        // 2. 调用service方法增加新员工信息
        return staffService.insertStaff(staff) ;
    }

    /**
     * 增加新用户，新教师,分配新的账号
     * <p><pre>{@code
     * 访问url: /user/addTeacher
     * 访问方式: {GET,POST}
     * 请求参数：username,sex,birthday,phone
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap ,如果 hashmap.get("state") ==1 则增加成功，hashmap.get("state") == 0 则增加失败
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping(value = "/addTeacher")
    public HashMap addTeacher(HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
        //1. 获取教师的基本信息
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String sex = request.getParameter("sex") ;
        String birthdays = request.getParameter("birthday") ;
        String phone = request.getParameter("phone") ;
        Teacher teacher = new Teacher(username,birthdays, sex,phone) ;

        //2. 返回教师增加的结果
        return teacherService.insertTeacher(teacher) ;
    }

    /**
     * 更新员工或者教师的密码
     * <p><pre>{@code
     * 访问url: /user/updateUserPassword
     * 访问方式: {GET,POST}
     * 请求参数：id(唯一标识符),password（旧密码）,passwordNew（新密码）
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap ,如果 hashmap.get("state") ==1 则修改成功，hashmap.get("state") == 0 则修改失败
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping("/updateUserPassword")
    public HashMap updateUserPassword(HttpServletRequest request){
        //1. 获取用户id，和新旧密码
        HashMap s = new HashMap() ;
        String id = request.getParameter("id");
        String password = request.getParameter("password") ;
        String passwordNew =  request.getParameter("passwordNew") ;

        //2. 根据用户id找到对应的用户，先和输入的旧密码验证
        //   借助MD5的验证方法，如果旧密码和数据库密码不一致则返回失败状态0，
        //   如果旧密码和数据库密码一致，则修改用户的密码，返回成功状态1
        try{
            User user = userService.findUserById(Long.valueOf(id));
            //验证旧密码是否和数据库密码一致
            if(user==null || !MD5Util.validPassword(password,user.getPassword()))
                s.put("state","0");//不一致返回0
            else{
                user.setPassword(MD5Util.getEncryptedPwd(passwordNew));
                userService.updateUser(user) ;
                s.put("state","1"); //一致修改密码后返回1
            }
        }catch (Exception e){
            e.printStackTrace();
            s.put("state","0");//异常返回0
        }finally {
            return s ;
        }
    }

}
