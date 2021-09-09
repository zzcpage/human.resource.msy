package com.service.impl;

import com.dao.CourseDao;
import com.dao.PayDao;
import com.dao.TeacherDao;
import com.dao.UserDao;
import com.domain.*;
import com.service.*;
import com.vo.TeacherPayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于提供教师业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.TeacherService
 **/
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private PayService payService;
    @Autowired
    private ResulteService resulteService;


    @Override
    public Teacher findTeacher(long id) {
        return teacherDao.findTeacherById(id);
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return teacherDao.findAllTeacher();
    }

    @Override
    public List<Teacher> findTeacherLimit(int offset, int pagesize) {
        return teacherDao.findTeacherLimit(offset, pagesize);
    }

    @Override
    public List<Teacher> findTeacherLimitAndSearch(int offset, int pagesize, String search) {
        return teacherDao.findTeacherLimitAndSearch(offset, pagesize, search);
    }

    @Override
    public List<Teacher> findAllTeacherSearch(int offset, int pagesize, String search) {
        return teacherDao.findAllTeacherSearch(  search);
    }

    @Override
    public HashMap findAdminTeacherCourseInfo(String search, int offset, int pageSize) {
        HashMap s = new HashMap();
        List<TeacherPayVo> teacherPayVos = new ArrayList<>();
        if (search == null || search.trim().equals("")) {
            s.put("total", findAllTeacher().size());   //返回数据总数
            List<Teacher> teacherList = findTeacherLimit(offset, pageSize);
            //计算老师的薪资
            for (Teacher teacher : teacherList) {
                teacherPayVos.add(new TeacherPayVo(teacher, payService.findTeacherPay(teacher.getId())));
            }
            s.put("rows", teacherPayVos); //返回页面数据
        } else {  //带有关键词的模糊查询
            s.put("total", findAllTeacherSearch(offset, pageSize, search).size());   //返回数据总数
            List<Teacher> teacherList = findTeacherLimitAndSearch(offset, pageSize, search);
            //计算老师的薪资
            for (Teacher teacher : teacherList) {
                teacherPayVos.add(new TeacherPayVo(teacher, payService.findTeacherPay(teacher.getId())));
            }
            s.put("rows", teacherPayVos); //返回页面数据
        }
        return s;
    }

    @Override
    public HashMap findAllTeacherInfo() {
        HashMap s = new HashMap();
        List<TeacherPayVo> teacherPayVos = new ArrayList<>();

        List<Teacher> teacherList = findAllTeacher();
        //计算老师的薪资
        for (Teacher teacher : teacherList) {
            teacherPayVos.add(new TeacherPayVo(teacher, payService.findTeacherPay(teacher.getId())));
        }
        s.put("rows", teacherPayVos); //返回页面数据

        return s;
    }

    @Override
    public HashMap insertTeacher(Teacher teacher) {
        //1. 新增教师用户权限为1
        HashMap s = new HashMap()  ;
        if(!userService.insertUser(new User(),teacher.getPhone(),1)){
            s.put("state","0"); //增加失败返回状态0
            return s ;
        }

        //2. 根据新增用户id，和教师基本信息新增一条教师记录
        User user = userService.findUser(teacher.getPhone()) ;
        teacher.setId(user.getId());
        if(!teacherDao.insertTeacher(teacher)){
            s.put("state","0");//增加失败返回状态0
            return s ;
        }
        s.put("state","1"); //增加成功返回状态1
        return s;
    }

    @Override
    public boolean deleteTeacher(Teacher teacher) {
        return teacherDao.deleteTeacher(teacher);
    }

    @Override
    public boolean deleteTeachers(List<Long> lst) {
        for (Long teacher : lst) {
            if(lectureService.findLectureByTid(teacher).size() !=0){
                return false ;
            }else{
                teacherDao.deleteTeacher(new Teacher(teacher));    //删除角色
                userService.deleteUser(new User(teacher));   //删除用户
            }
        }
        return true;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return teacherDao.updateTeacher(teacher);
    }
}
