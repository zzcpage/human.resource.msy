package com.controller;

import com.domain.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.service.*;
import com.vo.CourseVo;
import com.vo.StaffCourseVo;
import com.vo.StaffPageCourseVo;
import com.vo.TeacherCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Target;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用于课程模块的前后端交互
 *
 * @author : zzc
 * @version 1.1.0
 **/
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private ResulteService resulteService;
    @Autowired
    private PayService payService;

    /**
     * 增加课程信息
     * <p>传入课程的基本信息，然后添加新的课程</p>
     * <p><pre>{@code
     * 访问url: /course/addCourse
     * 访问方式: {GET,POST}
     * 请求参数：cname(课程名称),addr（地址）,time（课程时间）,intrduce（课程介绍）
     * num(开课人数)，phone（教师电话），qualified（合格员工薪资）,basic(基础薪资)
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return hashMap  s.map("state") = 1表明增加成功，s.map("state") = 0 表明增加失败
     */
    @ResponseBody
    @RequestMapping("/addCourse")
    public HashMap addCourse(HttpServletRequest request) {
        HashMap s = new HashMap();
        try {
            String cname = request.getParameter("cname");
            String addr = request.getParameter("addr");
            String time = request.getParameter("time");
            String intrduce = request.getParameter("intrduce");
            String num = request.getParameter("num");
            String phone = request.getParameter("phone"); //教师id
            String qualifield = request.getParameter("qualified");
            String basic = request.getParameter("basic");
            Course course = new Course(cname, addr, time, intrduce, Integer.valueOf(num));
            s = courseService.insertCourse(course, phone, qualifield, basic);
        } catch (Exception e) {
            e.printStackTrace();
            s.put("state", 0);
        } finally {
            return s;
        }
    }

    /**
     * 删除课程
     * <p>根据传入的课程id列表删除课程</p>
     * <p><pre>{@code
     * 访问url: /course/deleteCourse
     * 访问方式: {GET,POST}
     * 请求参数：userIds(课程id列表)
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return hashMap  map.("state“) = 0 表明删除失败，map.("state")=1表示删除成功
     */
    @ResponseBody
    @RequestMapping("/deleteCourse")
    public HashMap deleteCourse(HttpServletRequest request) {
        // 1. 获取需要删除的课程id列表
        HashMap s = new HashMap();
        s.put("state",0) ;
        String userIds = request.getParameter("userIds"); //获取删除课程uids
        //判断传入数据是否为空
        if (userIds == null || userIds.trim().equals("")) {
            s.put("state", 0);//返回错误信息
            return s;
        }

        //分割传递的id字符串，获取到各个课程id
        String[] userIdArr = userIds.split(",");
        //判断转化是否成功
        if (userIdArr == null) {
            s.put("state", 0);//返回错误信息
            return s;
        }

        List<Long> userIdList = new ArrayList<Long>();
        for (String userId : userIdArr) {
            userIdList.add(Long.valueOf(userId));
        }

        //2. 根据课程id列表删除各个课程信息
        try {
            if (courseService.deleteCourses(userIdList))
                s.put("state", 1); //删除成功，则设置成功状态为1
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 更新课程信息
     * <p>根据传入的课程id以及课程基本信息对课程信息进行更新操作</p>
     * <p><pre>{@code
     * 访问url: /course/updateCourse
     * 访问方式: {GET,POST}
     * 请求参数：id(课程id)，cname(课程名称),addr（地址）,time（课程时间）,intrduce（课程介绍）
     * num(开课人数)，phone（教师电话），qualified（合格员工薪资）,basic(基础薪资)
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return hashMap  map.("state“) = 0 表明更新失败，map.("state")=1表示更新成功
     */
    @ResponseBody
    @RequestMapping("/updateCourse")
    public HashMap updateCourse(HttpServletRequest request) {
        HashMap s = new HashMap();
        s.put("state", 0);
        try {
            //1. 获取课程的基本信息
            request.setCharacterEncoding("utf-8");
            String id = request.getParameter("id");
            String cname = request.getParameter("cname");
            String addr = request.getParameter("addr");
            String time = request.getParameter("time");
            String intrduce = request.getParameter("intrduce");
            String num = request.getParameter("num");
            String phone = request.getParameter("phone"); //教师id
            String qualifield = request.getParameter("qualified");
            String basic = request.getParameter("basic");

            //2. 封装为课程对象
            Course course = new Course(Long.valueOf(id), cname, addr, time, intrduce, Integer.valueOf(num));

            //3. 进行课程信息的更新
            s = courseService.updateCourseAndPayAndLecture(course, phone, qualifield, basic);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return s;
        }
    }

    /**
     * 管理员课程信息分页查询
     * <p>根据页面起始位置和页面大小以及模糊查询关键词进行查询课程信息</p>
     * <p><pre>{@code
     * 访问url: /course/limitPageCourse
     * 访问方式: {GET,POST}
     * 请求参数：page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     *
     * @param request 用户请求对象
     * @return 其中map.(" rows “)存放查询的课程信息数据, map.(" total ")存放课程记录总数
     */
    @ResponseBody
    @RequestMapping("/limitPageCourse")
    public HashMap selectCourseLimit(HttpServletRequest request) {
        //1. 获取请求参数的页码和页码大小以及模糊查询关键词
        HashMap s = new HashMap();
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 获取记录起始位置和数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 根据是否带有模糊查询关键词，分成带模糊查询和不带模糊查询的两个方法
        if (search == null || search.trim().equals("")) {   //不带模糊查询
            //1. 获取所有课程数目
            s.put("total", courseService.findAllCourse().size());
            //2. 获取指定区间的记录数，并且将结果封装成CourseVo视图对象
            List<Course> courseList = courseService.findCourseLimit(offset, pageSize);
            List<CourseVo> courseVoList = new ArrayList<>();
            for (Course course : courseList) {
                //获取授课老师信息
                Lecture lectures = lectureService.findLectureByCid(course.getId());
                Teacher teacher = teacherService.findTeacher(lectures.getTid());
                //获取薪资信息
                Pay pay = payService.findPayByCid(course.getId());
                //封装成视图对象
                if (teacher != null)
                    courseVoList.add(new CourseVo(course, Long.valueOf(teacher.getPhone()), pay));
            }
            s.put("rows", courseVoList); //返回页面数据
        } else {  //带有关键词的模糊查询
            //返回数据总数
            s.put("total", courseService.findAllCourseSearch(search).size());
            //2. 获取指定区间的记录数，并且将结果封装成CourseVo视图对象
            List<Course> courseList = courseService.findCourseLimitAndSearch(offset, pageSize, search);
            List<CourseVo> courseVoList = new ArrayList<>();
            for (Course course : courseList) {
                //获取授课老师信息
                Lecture lectures = lectureService.findLectureByCid(course.getId());
                Teacher teacher = teacherService.findTeacher(lectures.getTid());
                //获取薪资信息
                Pay pay = payService.findPayByCid(course.getId());
                //封装成视图对象
                if (teacher != null)
                    courseVoList.add(new CourseVo(course, Long.valueOf(teacher.getPhone()), pay));
            }
            s.put("rows", courseVoList); //返回页面数据
        }
        return s;
    }


    /**
     * 教师课程信息分页查询
     * <p>根据页面起始位置和页面大小以及模糊查询关键词进行查询课程信息</p>
     * <p><pre>{@code
     * 访问url: /course/limitPageCourse
     * 访问方式: {GET,POST}
     * 请求参数：id(教师唯一标识符),page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     *
     * @param request 用户请求对象
     * @return 其中map.(" rows “)存放查询的课程信息数据, map.(" total ")存放课程记录总数
     */
    @ResponseBody
    @RequestMapping("/limitTeacherPageCourse")
    public HashMap selectTeacherCourseLimit(HttpServletRequest request) {
        //1. 提取用户请求的教师id，页码，页面大小，模糊查询关键词
        HashMap s = new HashMap();
        String id = request.getParameter("id");
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 获取数据起始位置和数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 根据模糊查询关键词是否为空或为null，分成带模糊查询和不带模糊查询两种查询方式
        if (search == null || search.trim().equals("")) {
            //搜索教师id，所教授的所有课程记录数
            s.put("total", courseService.findTeacherAllCourse(Long.valueOf(id)).size());   //返回数据总数
            //搜索教师id下指定页面下的数据，并封装为TeacherCourseVo对象
            List<Course> courseList = courseService.findTeacherCourseLimit(offset, pageSize, Long.valueOf(id));
            List<TeacherCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据课程id搜索课程的薪资表
                Pay pay = payService.findPayByCid(course.getId());
                //封装教师课程信息
                HashMap hashMap = payService.findCoursePay(course.getId());
                List<Record> rlist = recordService.findRecordByCid(course.getId());
                String remainder = rlist.size() + "/" + course.getNum();
                TeacherCourseVo teacherCourseVo = new TeacherCourseVo(course, remainder);
                teacherCourseVo.setNum(course.getNum());//课程人数
                teacherCourseVo.setPeopleNum(rlist.size());//选课人数
                teacherCourseVo.setQualifiedNum((Integer) hashMap.get("qualified"));//合格人数
                teacherCourseVo.setQualified(pay.getQualified());   //设置合格员工薪资
                teacherCourseVo.setBasic(pay.getBasic()); //设置底薪
                teacherCourseVo.setPay((Double) hashMap.get("pay"));
                //封装成视图对象
                courseBeanList.add(teacherCourseVo);
            }
            s.put("rows", courseBeanList); //返回页面数据
        } else {  //带有关键词的模糊查询
            //搜索教师id，所教授的所有课程记录数
            s.put("total", courseService.findTeacherAllCourseSearch(offset, pageSize, search, Long.valueOf(id)).size());   //返回数据总数
            /*将数据进行封装*/
            List<Course> courseList = courseService.findCourseTeacherLimitAndSearch(offset, pageSize, search, Long.valueOf(id));
            List<TeacherCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据课程id搜索课程的薪资表
                Pay pay = payService.findPayByCid(course.getId());
                //搜索教师id下指定页面下的数据，并封装为TeacherCourseVo对象
                HashMap hashMap = payService.findCoursePay(course.getId());
                List<Record> rlist = recordService.findRecordByCid(course.getId());
                String remainder = rlist.size() + "/" + course.getNum();
                TeacherCourseVo teacherCourseVo = new TeacherCourseVo(course, remainder);
                teacherCourseVo.setNum(course.getNum());//课程人数
                teacherCourseVo.setPeopleNum(rlist.size());//选课人数
                teacherCourseVo.setQualifiedNum((Integer) hashMap.get("qualified"));//合格人数
                teacherCourseVo.setQualified(pay.getQualified());   //设置合格员工薪资
                teacherCourseVo.setBasic(pay.getBasic()); //设置底薪
                teacherCourseVo.setPay((Double) hashMap.get("pay"));
                //封装成视图对象
                courseBeanList.add(teacherCourseVo);
            }
            s.put("rows", courseBeanList); //返回页面数据
        }

        return s;
    }

    /**
     * 员工选课界面信息分页查询
     * <p>根据页面起始位置和页面大小以及模糊查询关键词进行查询课程信息</p>
     * <p><pre>{@code
     * 访问url: /course/limitStaffPageCourse
     * 访问方式: {GET,POST}
     * 请求参数：id(员工唯一标识符),page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     *
     * @param request 用户请求对象
     * @return 其中map.(" rows “)存放查询的课程信息数据, map.(" total ")存放课程记录总数
     */
    @ResponseBody
    @RequestMapping("/limitStaffPageCourse")
    public HashMap selectStaffCourseLimit(HttpServletRequest request) {
        //1. 获取请求参数中的员工id以及页码，页面大小，模糊查询关键词
        HashMap s = new HashMap();
        String id = request.getParameter("id");
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 获取数据起始位置和数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        // 将课程信息（未选择）返回给员工，返回信息包括课程基本信息，以及课程选课情况（即人数信息）
        if (search == null || search.trim().equals("")) {
            //获取员工所有未选择课程的总记录数
            s.put("total", courseService.findStaffAllCourse(Long.valueOf(id)).size());   //返回数据总数
            //将数据进行封装成StaffCourseVo视图对象
            List<Course> courseList = courseService.findStaffCourseLimit(offset, pageSize, Long.valueOf(id));
            List<StaffCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据授课记录查询教师信息
                Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
                if(teacher != null){
                    //封装教师课程信息
                    List<Record> rlist = recordService.findRecordByCid(course.getId());
                    String remainder = rlist.size() + "/" + course.getNum();
                    StaffCourseVo staffCourseVo = new StaffCourseVo(course, remainder);
                    staffCourseVo.setUsername(teacher.getUsername());
                    //封装成视图对象
                    courseBeanList.add(staffCourseVo);
                }

            }
            s.put("rows", courseBeanList); //返回页面数据
        } else {  //带有关键词的模糊查询
            //获取员工所有未选择课程的总记录数
            s.put("total", courseService.findStaffAllCourseSearch(search, Long.valueOf(id)).size());   //返回数据总数
            //将数据进行封装成StaffCourseVo视图对象
            List<Course> courseList = courseService.findCourseStaffLimitAndSearch(offset, pageSize, search, Long.valueOf(id));
            List<StaffCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据授课记录查询教师信息
                Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
                if(teacher != null){
                    //封装教师课程信息
                    List<Record> rlist = recordService.findRecordByCid(course.getId());
                    String remainder = rlist.size() + "/" + course.getNum();
                    StaffCourseVo staffCourseVo = new StaffCourseVo(course, remainder);
                    staffCourseVo.setUsername(teacher.getUsername());
                    //封装成视图对象
                    courseBeanList.add(staffCourseVo);
                }
            }
            s.put("rows", courseBeanList); //返回页面数据
        }
        return s;
    }


    /**
     * 员工已选课程信息分页查询
     * <p>根据页面起始位置和页面大小以及模糊查询关键词进行查询课程信息</p>
     * <p><pre>{@code
     * 访问url: /course/limitPageCourse
     * 访问方式: {GET,POST}
     * 请求参数：id(员工唯一标识符),page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     *
     * @param request 用户请求对象
     * @return 其中map.(" rows “)存放查询的课程信息数据, map.(" total ")存放课程记录总数
     */
    @ResponseBody
    @RequestMapping("/limitStaffPageSelectCourse")
    public HashMap selectStaffSelectCourseLimit(HttpServletRequest request) {
        //1. 提取用户请求中的用户id，页码，页面大小，搜索关键词
        HashMap s = new HashMap();
        String id = request.getParameter("id");
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 确定数据起始位置和数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 将员工已选课程信息，根据分页查询结果返回给页面
        if (search == null || search.trim().equals("")) {//不带模糊查询的分页查询
            //搜索员工所有选课记录数
            s.put("total", courseService.findStaffAllSelectCourse(Long.valueOf(id)).size());   //返回数据总数
            //将数据封装成StaffCourseVo视图对象
            List<Course> courseList = courseService.findStaffSelectCourseLimit(offset, pageSize, Long.valueOf(id));
            List<StaffCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据授课记录查询到教师信息
                Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
                //封装教师课程信息
                List<Record> rlist = recordService.findRecordByCid(course.getId());
                String remainder = rlist.size() + "/" + course.getNum();
                StaffCourseVo staffCourseVo = new StaffCourseVo(course, remainder);
                staffCourseVo.setUsername(teacher.getUsername());
                //封装成视图对象
                courseBeanList.add(staffCourseVo);
            }
            s.put("rows", courseBeanList); //返回页面数据
        } else {  //带有关键词的模糊查询
            //搜索员工所有选课记录数
            s.put("total", courseService.findStaffAllSelectCourseSearch(search, Long.valueOf(id)).size());   //返回数据总数
            //将数据封装成StaffCourseVo视图对象
            List<Course> courseList = courseService.findCourseStaffSelectLimitAndSearch(offset, pageSize, search, Long.valueOf(id));
            List<StaffCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据授课记录查询到教师信息
                Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
                //封装教师课程信息
                List<Record> rlist = recordService.findRecordByCid(course.getId());
                String remainder = rlist.size() + "/" + course.getNum();
                StaffCourseVo staffCourseVo = new StaffCourseVo(course, remainder);
                staffCourseVo.setUsername(teacher.getUsername());
                //封装成视图对象
                courseBeanList.add(staffCourseVo);
            }
            s.put("rows", courseBeanList); //返回页面数据
        }
        return s;
    }

    /**
     * 员工查询已选课程的成绩
     * <p>根据页面起始位置和页面大小以及模糊查询关键词进行查询课程信息</p>
     * <p><pre>{@code
     * 访问url: /course/limitStaffPageSelectCourseGrade
     * 访问方式: {GET,POST}
     * 请求参数：id(员工唯一标识符),page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     *
     * @param request 用户请求对象
     * @return 其中map.(" rows “)存放查询的课程信息数据, map.(" total ")存放课程记录总数
     */
    @ResponseBody
    @RequestMapping("/limitStaffPageSelectCourseGrade")
    public HashMap selectStaffSelectCourseLimitGrade(HttpServletRequest request) {
        //1. 根据用户请求提取出用户的id，页码，页面大小，模糊查询关键词
        HashMap s = new HashMap();
        String id = request.getParameter("id");
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 确定数据起始位置和数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 将员工分页查询的成绩返回给页面
        if (search == null || search.trim().equals("")) {
            //查询员工已选课程的记录数
            s.put("total", courseService.findStaffAllSelectCourse(Long.valueOf(id)).size());   //返回数据总数
            //将员工已选课程信息和成绩信息封装成StaffPageCourseVo视图对象
            List<Course> courseList = courseService.findStaffSelectCourseLimit(offset, pageSize, Long.valueOf(id));
            List<StaffPageCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据授课信息获取到教师信息
                Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
                //封装教师课程信息
                Resulte resulte = resulteService.findResulte(Long.valueOf(id), course.getId());
                Record record = recordService.findRecordByUidAndCid(Long.valueOf(id), course.getId());
                StaffPageCourseVo staffCourseBean = new StaffPageCourseVo();
                staffCourseBean.setUsername(teacher.getUsername());
                staffCourseBean.setId(course.getId());
                staffCourseBean.setAddr(course.getAddr());
                staffCourseBean.setIntrduce(course.getIntrduce());
                staffCourseBean.setTime(course.getTime());
                staffCourseBean.setCname(course.getCname());
                if (resulte == null || record.getState() == 0) {
                    staffCourseBean.setGrade(String.valueOf("暂未录入"));
                } else {
                    staffCourseBean.setGrade(String.valueOf(resulte.getGrade()));
                }
                //将课程信息和成绩信息封装成视图对象
                courseBeanList.add(staffCourseBean);
            }
            s.put("rows", courseBeanList); //返回页面数据
        } else {  //带有关键词的模糊查询
            //查询员工已选课程的记录数
            s.put("total", courseService.findStaffAllSelectCourseSearch(search, Long.valueOf(id)).size());   //返回数据总数
            //将员工已选课程信息和成绩信息封装成StaffPageCourseVo视图对象
            List<Course> courseList = courseService.findCourseStaffSelectLimitAndSearch(offset, pageSize, search, Long.valueOf(id));
            List<StaffPageCourseVo> courseBeanList = new ArrayList<>();
            for (Course course : courseList) {
                //根据授课信息获取到教师信息
                Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
                //封装教师课程信息
                Resulte resulte = resulteService.findResulte(Long.valueOf(id), course.getId());
                Record record = recordService.findRecordByUidAndCid(Long.valueOf(id), course.getId());
                StaffPageCourseVo staffCourseBean = new StaffPageCourseVo();
                staffCourseBean.setUsername(teacher.getUsername());
                staffCourseBean.setId(course.getId());
                staffCourseBean.setAddr(course.getAddr());
                staffCourseBean.setIntrduce(course.getIntrduce());
                staffCourseBean.setTime(course.getTime());
                staffCourseBean.setCname(course.getCname());
                if (resulte == null || record.getState() == 0) {
                    staffCourseBean.setGrade(String.valueOf("暂未录入"));
                } else {
                    staffCourseBean.setGrade(String.valueOf(resulte.getGrade()));
                }
                //将课程信息和成绩信息封装成视图对象
                courseBeanList.add(staffCourseBean);
            }
            s.put("rows", courseBeanList); //返回页面数据
        }
        return s;
    }

    /**
     * 用于下载教师的课程信息
     * <p>根据传入的教师id返回对应课程信息的pdf</p>
     * <p><pre>{@code
     * 访问url: /course/downloadCourse
     * 访问方式: {GET,POST}
     * 请求参数：tid(教师唯一标识符)
     * }</pre></p>
     *
     * @param request  用户请求对象
     * @param response 服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadCourse")
    public void downloadTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //1.设置请求头部信息
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        /*初始化信息*/
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        /*数据准备*/

        //获取教师的所有课程的TeacherCourseVo视图对象
        Teacher teacher = teacherService.findTeacher(Long.valueOf(request.getParameter("tid")));
        List<TeacherCourseVo> courseBeanList = new ArrayList<>();
        if(teacher!=null){
            List<Course> courseList = courseService.findTeacherAllCourse(Long.valueOf(request.getParameter("tid")));
            for (Course course : courseList) {
                Pay pay = payService.findPayByCid(course.getId());
                //封装教师课程信息
                if(pay!=null){
                    HashMap hashMap = payService.findCoursePay(course.getId());
                    List<Record> rlist = recordService.findRecordByCid(course.getId());
                    String remainder = rlist.size() + "/" + course.getNum();
                    TeacherCourseVo teacherCourseVo = new TeacherCourseVo(course, remainder);
                    teacherCourseVo.setNum(course.getNum());//课程人数
                    teacherCourseVo.setPeopleNum(rlist.size());//选课人数
                    teacherCourseVo.setQualifiedNum((Integer) hashMap.get("qualified"));//合格人数
                    teacherCourseVo.setQualified(pay.getQualified());   //设置合格员工薪资
                    teacherCourseVo.setBasic(pay.getBasic()); //设置底薪
                    teacherCourseVo.setPay((Double) hashMap.get("pay"));
                    courseBeanList.add(teacherCourseVo);
                }
            }
        }


        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/" + new Date().getTime();// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {
            myPath.mkdirs();
        }
        String filePath = null;
        filePath = filePar + "/" + new Date().getTime() + ".pdf";
        file = new File(filePath);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        //设置中文字体
        BaseFont bfChinese = null;// FontFactory.getFont(FontFactory.COURIER,
        // 14, Font.BOLD, new CMYKColor(0, 255, 0, 0);//大小，粗细，颜色
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        //设置字体样式
        Font f10 = new Font(bfChinese, 10, Font.NORMAL);
        Font f12 = new Font(bfChinese, 12, Font.NORMAL);
        Font f26 = new Font(bfChinese, 26, Font.NORMAL);//一号字体
        //创建标题
        Paragraph title1 = new Paragraph(teacher.getUsername() + "课程表", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建3列的表格
        int colNumber = 3;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {0.55f, 0.2f, 0.55f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("课程名", f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("时间", f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("地点", f12));
        t.addCell(c3);
        //表格内容填充
        for (TeacherCourseVo teacherCourseVo : courseBeanList) {
            t.addCell(new Paragraph(teacherCourseVo.getCname(), f10));
            t.addCell(new Paragraph(teacherCourseVo.getTime(), f10));
            t.addCell(new Paragraph(teacherCourseVo.getAddr(), f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            //设置响应头
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename = teacher.getUsername() + "的课程表";
            // 设置浏览器以下载的方式处理该文件
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");//下载使用
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
            if (myPath != null)// 删除临时文件夹
                myPath.delete();
        }
    }

    /**
     * 用于管理员下载所有课程信息
     * <p>下载所有课程信息的pdf</p>
     * <p><pre>{@code
     * 访问url: /course/downloadAllCourse
     * 访问方式: {GET,POST}
     * }</pre></p>
     *
     * @param request  用户请求对象
     * @param response 服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadAllCourse")
    public void downloadAllCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //1. 设置响应头信息
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        /*数据初始化*/
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        /*数据准备*/
        //查询所有的课程信息，封装成CourseVo视图对象
        List<Course> courseList = courseService.findAllCourse();
        List<CourseVo> courseVoList = new ArrayList<>();
        for (Course course : courseList) {
            Lecture lectures = lectureService.findLectureByCid(course.getId());
            if(lectures == null)
                continue;
            Teacher teacher = teacherService.findTeacher(lectures.getTid());
            if(teacher == null)
                continue;
            Pay pay = payService.findPayByCid(course.getId());
            if(pay == null)
                continue;
            courseVoList.add(new CourseVo(course, Long.valueOf(teacher.getPhone()), pay));
        }
        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/" + new Date().getTime();// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {
            myPath.mkdirs();
        }
        String filePath = null;
        filePath = filePar + "/" + new Date().getTime() + ".pdf";
        file = new File(filePath);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        //设置中文字体
        BaseFont bfChinese = null;// FontFactory.getFont(FontFactory.COURIER,
        // 14, Font.BOLD, new CMYKColor(0, 255, 0, 0);//大小，粗细，颜色
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        Font f10 = new Font(bfChinese, 10, Font.NORMAL);
        Font f12 = new Font(bfChinese, 12, Font.NORMAL);
        Font f26 = new Font(bfChinese, 26, Font.NORMAL);//一号字体
        //创建标题
        Paragraph title1 = new Paragraph("课程表", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建8列的表格
        int colNumber = 8;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {0.55f, 0.2f, 0.55f, 0.55f, 0.3f, 0.2f, 0.2f, 0.4f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("课程名", f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("时间", f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("地点", f12));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("介绍", f12));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("电话", f12));
        t.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Paragraph("限制人数", f12));
        t.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Paragraph("员工合格薪资/人", f12));
        t.addCell(c7);
        PdfPCell c8 = new PdfPCell(new Paragraph("基础工资", f12));
        t.addCell(c8);
        //数据填充
        for (CourseVo courseVo : courseVoList) {
            t.addCell(new Paragraph(courseVo.getCname(), f10));
            t.addCell(new Paragraph(courseVo.getTime(), f10));
            t.addCell(new Paragraph(courseVo.getAddr(), f10));
            t.addCell(new Paragraph(courseVo.getIntrduce(), f10));
            t.addCell(new Paragraph(String.valueOf(courseVo.getPhone()), f10));
            t.addCell(new Paragraph(String.valueOf(courseVo.getNum()), f10));
            t.addCell(new Paragraph(String.valueOf(courseVo.getQualified()), f10));
            t.addCell(new Paragraph(String.valueOf(courseVo.getBasic()), f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename = "课程表";
            // 设置浏览器以下载的方式处理该文件
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");//下载使用
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
            if (myPath != null)// 删除临时文件夹
                myPath.delete();
        }
    }

    /**
     * 用于教师下载所教授课程的薪资情况表
     * <p>根据传入的教师id返回对应课程薪资信息的pdf</p>
     * <p><pre>{@code
     * 访问url: /course/downloadTeacherCoursePay
     * 访问方式: {GET,POST}
     * 请求参数：tid(教师唯一标识符)
     * }</pre></p>
     *
     * @param request  用户请求对象
     * @param response 服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadTeacherCoursePay")
    public void downloadTeacherCoursePay(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //1.设置请求头部信息
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        /*初始化信息*/
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        /*数据准备*/
        //根据教师tid查询所教授的所有课程
        //将课程信息和薪资信息封装成TeacherCourseVo视图对象
        List<Course> courseList = courseService.findTeacherAllCourse(Long.valueOf(request.getParameter("tid")));
        Teacher teacher = teacherService.findTeacher(Long.valueOf(request.getParameter("tid")));
        List<TeacherCourseVo> courseBeanList = new ArrayList<>();
        for (Course course : courseList) {
            Pay pay = payService.findPayByCid(course.getId());
            //封装教师课程信息
            HashMap hashMap = payService.findCoursePay(course.getId());
            List<Record> rlist = recordService.findRecordByCid(course.getId());
            String remainder = rlist.size() + "/" + course.getNum();
            TeacherCourseVo teacherCourseVo = new TeacherCourseVo(course, remainder);
            teacherCourseVo.setNum(course.getNum());//课程人数
            teacherCourseVo.setPeopleNum(rlist.size());//选课人数
            teacherCourseVo.setQualifiedNum((Integer) hashMap.get("qualified"));//合格人数
            teacherCourseVo.setQualified(pay.getQualified());   //设置合格员工薪资
            teacherCourseVo.setBasic(pay.getBasic()); //设置底薪
            teacherCourseVo.setPay((Double) hashMap.get("pay"));
            courseBeanList.add(teacherCourseVo);
        }
        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/" + new Date().getTime();// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {
            myPath.mkdirs();
        }
        String filePath = null;
        filePath = filePar + "/" + new Date().getTime() + ".pdf";
        file = new File(filePath);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        //设置中文字体
        BaseFont bfChinese = null;// FontFactory.getFont(FontFactory.COURIER,
        // 14, Font.BOLD, new CMYKColor(0, 255, 0, 0);//大小，粗细，颜色
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        Font f10 = new Font(bfChinese, 10, Font.NORMAL);
        Font f12 = new Font(bfChinese, 12, Font.NORMAL);
        Font f26 = new Font(bfChinese, 26, Font.NORMAL);//一号字体
        //创建标题
        Paragraph title1 = new Paragraph(teacher.getUsername() + "薪资表", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建10列的表格
        int colNumber = 10;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {0.55f, 0.2f, 0.55f, 0.55f, 0.3f, 0.3f, 0.3f, 0.3f, 0.3f, 0.3f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("课程名", f12));
        t.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Paragraph("地点", f12));
        t.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Paragraph("时间", f12));
        t.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Paragraph("介绍", f12));
        t.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Paragraph("限制人数", f12));
        t.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Paragraph("员工合格薪资/人", f12));
        t.addCell(c6);

        PdfPCell c7 = new PdfPCell(new Paragraph("基础工资", f12));
        t.addCell(c7);

        PdfPCell c8 = new PdfPCell(new Paragraph("选课人数", f12));
        t.addCell(c8);

        PdfPCell c9 = new PdfPCell(new Paragraph("合格人数", f12));
        t.addCell(c9);

        PdfPCell c10 = new PdfPCell(new Paragraph("薪资", f12));
        t.addCell(c10);

        //表格数据填充
        for (TeacherCourseVo teacherCourseVo : courseBeanList) {
            t.addCell(new Paragraph(teacherCourseVo.getCname(), f10));
            t.addCell(new Paragraph(teacherCourseVo.getAddr(), f10));
            t.addCell(new Paragraph(teacherCourseVo.getTime(), f10));

            t.addCell(new Paragraph(teacherCourseVo.getIntrduce(), f10));
            t.addCell(new Paragraph(String.valueOf(teacherCourseVo.getNum()), f10));
            t.addCell(new Paragraph(String.valueOf(teacherCourseVo.getQualified()), f10));

            t.addCell(new Paragraph(String.valueOf(teacherCourseVo.getBasic()), f10));
            t.addCell(new Paragraph(String.valueOf(teacherCourseVo.getPeopleNum()), f10));
            t.addCell(new Paragraph(String.valueOf(teacherCourseVo.getQualifiedNum()), f10));
            t.addCell(new Paragraph(String.valueOf(teacherCourseVo.getPay()), f10));

        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename = teacher.getUsername() + "的薪资表";
            // 设置浏览器以下载的方式处理该文件
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");//下载使用
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
            if (myPath != null)// 删除临时文件夹
                myPath.delete();
        }
    }


}
