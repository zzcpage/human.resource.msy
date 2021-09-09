package com.service.impl;

import com.dao.StaffDao;
import com.dao.UserDao;
import com.domain.Staff;
import com.domain.User;
import com.service.CourseService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.StaffService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于提供员工业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.StaffService
 **/
@Service("staffService")
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao ;
    @Autowired
    private UserDao userDao ;
    @Autowired
    private UserService userService ;
    @Autowired
    private CourseService courseService ;
    @Override
    public Staff findStaff(long id) {
        return staffDao.findStaffById(id);
    }

    @Override
    public List<Staff> findAllStaff() {
        return staffDao.findAllStaff();
    }

    @Override
    public List<Staff> findStaffLimit(int offset, int pagesize) {
        return staffDao.findStaffLimit(offset,pagesize);
    }

    @Override
    public List<Staff> findStaffLimitAndSearch(int offset, int pagesize, String search) {
        return staffDao.findStaffLimitAndSearch(offset,pagesize,search);
    }

    @Override
    public List<Staff> findAllStaffSearch(  String search) {
        return staffDao.findAllStaffSearch( search);
    }


    @Override
    public HashMap insertStaff(Staff staff) {
        //1. 新增一个用户，权限为2表示员工权限
        HashMap s = new HashMap() ;
        if(!userService.insertUser(new User(),staff.getPhone(),2)){
            s.put("state","0");//加入失败返回状态0
            return s ;
        }

        //2. 用户添加成功后获取新增用户的id，然后将员工信息加入员工表
        User user = userService.findUser(staff.getPhone()) ;
        staff.setId(user.getId());
        if(!staffDao.insertStaff(staff)){
            s.put("state","0"); //加入失败返回状态0
            return s ;
        }
        s.put("state","1");//增加成功，返回状态1
        return s;
    }

    @Override
    public boolean deleteStaff(Staff staff) {
        return staffDao.deleteStaff(staff);
    }

    @Override
    public boolean deleteStaffs(List<Long> lst) {
        for(Long staff : lst){
            if(courseService.findStaffAllCourse(staff).size() !=0){
                staffDao.deleteStaff(new Staff(staff)) ;   // 删除角色
                userDao.deleteUser(staff) ; //删除用户
            }else{
                return false ;
            }
        }
        return true ;
    }

    @Override
    public boolean updateStaff(Staff staff) {
        /*需要更新用户的信息*/
        User user = new User() ;
        user.setAccount(staff.getPhone());
        user.setId(staff.getId());
        userDao.updateAccount(user);
        return staffDao.updateStaff(staff);
    }

    @Override
    public List<Staff> findAllCourceStaff(Long valueOf) {
        return staffDao.findAllTeacherStaff(valueOf);
    }

    @Override
    public List<Staff> findCourceStaffLimit(int offset, int pageSize, Long valueOf) {
        return staffDao.findTeacherStaffLimit(offset,pageSize,valueOf);
    }

    @Override
    public List<Staff> findAllCourceStaffSearch(  String search, Long valueOf) {
        return staffDao.findAllTeacherStaffSearch( search,valueOf);
    }

    @Override
    public List<Staff> findStaffCourceLimitAndSearch(int offset, int pageSize, String search, Long valueOf) {
        return staffDao.findTeacherStaffLimitAndSearch(offset,pageSize,search,valueOf);
    }
}
