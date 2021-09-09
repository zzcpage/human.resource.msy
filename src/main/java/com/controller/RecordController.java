package com.controller;

import com.domain.Course;
import com.domain.Record;
import com.domain.Resulte;
import com.domain.Staff;
import com.service.CourseService;
import com.service.RecordService;
import com.service.ResulteService;
import com.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于选课记录模块的前后端交互
 * @author : zzc
 * @version 1.1.0
 **/
@Controller
@RequestMapping(value = "/record")
public class RecordController {
    @Autowired
    private RecordService recordService ;
    @Autowired
    private CourseService courseService ;
    @Autowired
    private StaffService staffService ;
    @Autowired
    private ResulteService resulteService;

    /**
     * 查询员工所有选课信息
     * <p>根据传入的员工id返回员工所选课程列表</p>
     * <p><pre>{@code
     * 访问url: /record/getRecourd
     * 访问方式: {GET,POST}
     * 请求参数：id(员工的id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return List<Course> 返回员工所选课程列表
     */
    @ResponseBody
    @RequestMapping(value = "/getRecourd")
    public List<Course> findCourseByUid(HttpServletRequest request){
        List<Course> clist = new ArrayList<>() ;
        try{
            //1. 获取到id值，用于进行查询员工所选的所有课程
            String ids = request.getParameter("id") ;
            Long id = Long.valueOf(ids) ;
            //2. 查询到用户选课的id
            List<Record> rlist = recordService.findRecordByUid(id) ;
            //3. 根据课程id , 返回课程信息给用户
            for(Record record : rlist){
                clist.add(courseService.findCourse(record.getCid())) ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return clist ;
        }
    }

    /**
     * 查询课程下的选课员工信息
     * <p>根据传入的课程id返回课程下的选课成员信息</p>
     * <p><pre>{@code
     * 访问url: /record/getStaff
     * 访问方式: {GET,POST}
     * 请求参数：id(课程的id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return List<Staff> 返回课程下所有员工信息
     */
    @ResponseBody
    @RequestMapping(value = "/getStaff")
    public List<Staff> findStaffByCid(HttpServletRequest request){
        List<Staff> slist = new ArrayList<>() ;
        try{
            //1. 获取到id值，用于进行查询课程下所有选课成员
            String ids = request.getParameter("id") ;
            Long id = Long.valueOf(ids) ;
            //2. 查询到用户选课的id
            List<Record> rlist = recordService.findRecordByCid(id) ;
            //3. 根据课程id , 返回课程信息给用户
            for(Record record : rlist){
                slist.add(staffService.findStaff(record.getUid())); ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return slist ;
        }
    }

    /**
     * 删除选课记录
     * <p>根据传入的课程cid和员工uid删除选课记录</p>
     * <p><pre>{@code
     * 访问url: /record/deleteRecord
     * 访问方式: {GET,POST}
     * 请求参数：cid(课程的id)，uid(员工的id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return  如果map.("state") = 1则删除成功，如果map.("state") = 0 则删除失败
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRecord")
    public HashMap deleteRecord(HttpServletRequest request){
        //1. 获取需要删除的员工uid 和 课程cid 用于定位需要删除的记录
        HashMap s = new HashMap() ;
        s.put("state",0) ;
        String uid = request.getParameter("uid");
        String cid = request.getParameter("cid") ;
        //2. 进行记录的删除
        if(recordService.deleteRecordAndResulte(new Record(Long.valueOf(uid),Long.valueOf(cid)),new Resulte(Long.valueOf(uid),Long.valueOf(cid))))
            s.put("state",1);
        return s;
    }

    /**
     * 增加选课记录
     * <p>根据传入的课程cid和员工的uid新增一条选课记录</p>
     * <p><pre>{@code
     * 访问url: /record/addRecord
     * 访问方式: {GET,POST}
     * 请求参数：id(课程的id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return  如果map.("state") = 1则删除成功，如果map.("state") = 0 则删除失败，如果map.("state") = 2表明选课人数已满
     */
    @ResponseBody
    @RequestMapping(value = "/addRecord")
    public HashMap insertRecord(HttpServletRequest request){
        HashMap s = new HashMap() ;
        try{
            String id = request.getParameter("uid") ;
            String cid = request.getParameter("cid");
            s = recordService.insertStaffRecord(Long.valueOf(cid),Long.valueOf(id));
        }catch (Exception e){
            e.printStackTrace();
            s.put("state",0);
        }finally {
            return s ;
        }
    }

}
