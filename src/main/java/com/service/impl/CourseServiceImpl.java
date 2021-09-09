package com.service.impl;

import com.dao.CourseDao;
import com.dao.LectureDao;
import com.dao.PayDao;
import com.domain.*;
import com.service.*;
import com.vo.ResulteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 用于提供课程业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.CourseService
 **/
@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private LectureService lectureService ;
    @Autowired
    private ResulteService resulteService;
    @Autowired
    private StaffService staffService ;
    @Autowired
    private CourseService courseService ;
    @Autowired
    private PayService payService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private RecordService recordService ;
    @Override
    public Course findCourse(long id) {
        return courseDao.findCourseById(id);
    }

    @Override
    public boolean insertCourse(Course course) {
        return courseDao.insertCourse(course);
    }

    @Override
    public boolean deleteCourse(Course course) {
        return courseDao.deleteCourse(course);
    }

    @Override
    public boolean updateCourse(Course course) {
        return courseDao.updateCourse(course);
    }

    @Override
    public boolean deleteCourses(List<Long> lst) {
        boolean flag = false ;
        try{
            for(Long staff : lst){
                if(recordService.findRecordByCid(staff).size()==0){
                    lectureService.deleteLectureByCid(new Lecture(staff)) ;//删除授课记录
                    courseDao.deleteCourse(new Course(staff)) ;       //删除课程记录
                    payService.deletePay(new Pay(staff)) ;               //删除薪资记录
                }else{
                    return false ;
                }
            }
            flag = true ;
        }finally {
            return flag ;
        }
    }

    @Override
    public HashMap findCourseStaffGrade(long cid, String search, int pageSize, int offset) {
        HashMap s = new HashMap() ;
        //3. 根据是否带有模糊查询关键词进行课程下的用户成绩搜索
        if (search == null || search.trim().equals("")) {
            s.put("total", resulteService.findResultByCid(Long.valueOf(cid)).size());   //返回数据总数
            List<Resulte> resulteList = resulteService.findResulteLimit(offset, pageSize, Long.valueOf(cid));
            List<ResulteVo> resulteVos = new ArrayList<>();
            for (Resulte resulte : resulteList) {

                //1. 根据成绩记录的uid，找到员工
                Staff staff = staffService.findStaff(resulte.getUid());
                if(staff != null){
                    //2. 将员工信息和成绩封装成resulteBean对象
                    ResulteVo resulteVo = new ResulteVo();
                    resulteVo.setGrade(resulte.getGrade());
                    resulteVo.setId(staff.getId());
                    resulteVo.setBirthday(staff.getBirthday());
                    resulteVo.setDepartment(staff.getDepartment());
                    resulteVo.setPhone(staff.getPhone());
                    resulteVo.setSex(staff.getSex());
                    resulteVo.setUsername(staff.getUsername());

                    //3. 将对象添加到结果集列表
                    resulteVos.add(resulteVo);
                }


            }
            s.put("rows", resulteVos); //返回页面数据
        } else {  //带有关键词的模糊查询
            s.put("total", resulteService.findAllResulteSearch( search, Long.valueOf(cid)).size());   //返回数据总数
            List<Resulte> resulteList = resulteService.findResulteLimitAndSearch(offset, pageSize, search, Long.valueOf(cid));
            List<ResulteVo> resulteVos = new ArrayList<>();
            for (Resulte resulte : resulteList) {
                Staff staff = staffService.findStaff(resulte.getUid());
                if(staff != null){
                    //2. 将员工信息和成绩封装成resulteBean对象
                    ResulteVo resulteVo = new ResulteVo();
                    resulteVo.setGrade(resulte.getGrade());
                    resulteVo.setId(staff.getId());
                    resulteVo.setBirthday(staff.getBirthday());
                    resulteVo.setDepartment(staff.getDepartment());
                    resulteVo.setPhone(staff.getPhone());
                    resulteVo.setSex(staff.getSex());
                    resulteVo.setUsername(staff.getUsername());

                    //3. 将对象添加到结果集列表
                    resulteVos.add(resulteVo);
                }
            }
            s.put("rows", resulteVos);
        }
        return s  ;
    }

    @Override
    public HashMap insertCourse(Course course,String phone,String qualifield , String basic) {
         HashMap s = new HashMap();
         s.put("state",0);
         try{
             //增加课程信息
             courseService.insertCourse(course) ;
             User user = userService.findUser(phone) ;
             //增加授课记录
             Lecture lecture = new Lecture(user.getId(),course.getId());
             lectureService.inserLecture(lecture) ;
             //增加课程薪资记录
             payService.insertPay(new Pay(course.getId(),Double.valueOf(qualifield),Double.valueOf(basic))) ;
             s.put("state",1) ;
         }finally {
             return s  ;
         }
    }

    @Override
    public HashMap updateCourseAndPayAndLecture(Course course, String phone, String qualifield, String basic) {
         HashMap s = new HashMap() ;
         s.put("state",0) ;
         try{
             //更新课程信息
             courseService.updateCourse(course);
             //由于可能修改了授课老师电话，则需要调整授课表
             //需要更新授课老师信息，先删除原先信息，再增加新信息
             lectureService.deleteLectureByCid(new Lecture(course.getId()));
             User user = userService.findUser(phone) ;
             Lecture lecture = new Lecture(user.getId(),course.getId());
             lectureService.inserLecture(lecture) ;
             //同时更新薪资信息
             Pay pay = new Pay(course.getId(),Double.valueOf(qualifield),Double.valueOf(basic)) ;
             payService.updatePay(pay) ;
             s.put("state",1);
         }finally {
             return s ;
         }
    }

    @Override
    public List<Course> findAllCourse() {
        return courseDao.findAllCourse();
    }

    @Override
    public List<Course> findCourseLimit(int offset, int pagesize) {
        return courseDao.findCourseLimit(offset,pagesize);
    }

    @Override
    public List<Course> findCourseLimitAndSearch(int offset, int pagesize, String search) {
        return courseDao.findCourseLimitAndSearch(offset,pagesize,search);
    }

    @Override
    public List<Course> findAllCourseSearch(  String search) {
        return courseDao.findAllCourseSearch( search);
    }

    @Override
    public List<Course> findTeacherAllCourse(Long valueOf) {
        return courseDao.findTeacherAllCourse(valueOf);
    }

    @Override
    public List<Course> findTeacherCourseLimit(int offset, int pageSize, Long valueOf) {
        return  courseDao.findTeacherCourseLimit(offset,pageSize,valueOf);
    }

    @Override
    public List<Course> findTeacherAllCourseSearch(int offset, int pageSize, String search, Long valueOf) {
        return courseDao.findTeacherAllCourseSearch( search,valueOf);
    }

    @Override
    public List<Course> findCourseTeacherLimitAndSearch(int offset, int pageSize, String search, Long valueOf) {
        return courseDao.findTeacherCourseLimitAndSearch(offset,pageSize,search,valueOf);
    }

    @Override
    public List<Course> findStaffAllCourse(Long valueOf) {
        return courseDao.findStaffAllCourse(valueOf);
    }

    @Override
    public List<Course> findStaffCourseLimit(int offset, int pageSize, Long valueOf) {
        return courseDao.findStaffCourseLimit(offset,pageSize,valueOf);
    }

    @Override
    public List<Course> findStaffAllCourseSearch(  String search, Long valueOf) {
        return courseDao.findStaffAllCourseSearch(search,valueOf);
    }

    @Override
    public List<Course> findCourseStaffLimitAndSearch(int offset, int pageSize, String search, Long valueOf) {
        return courseDao.findStaffCourseLimitAndSearch(offset,pageSize,search,valueOf);
    }

    @Override
    public List<Course> findStaffAllSelectCourse(Long valueOf) {
        return courseDao.findStaffAllSelectCourse(valueOf);
    }

    @Override
    public List<Course> findStaffSelectCourseLimit(int offset, int pageSize, Long valueOf) {
        return courseDao.findStaffSelectCourseLimit(offset,pageSize,valueOf) ;
    }

    @Override
    public List<Course> findStaffAllSelectCourseSearch(String search, Long valueOf) {
        return courseDao.findStaffAllCourseSearch( search,valueOf);
    }

    @Override
    public List<Course> findCourseStaffSelectLimitAndSearch(int offset, int pageSize, String search, Long valueOf) {
        return courseDao.findStaffSelectCourseLimitAndSearch(offset,pageSize,search,valueOf);
    }


}
