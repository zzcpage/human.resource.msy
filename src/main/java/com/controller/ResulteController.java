package com.controller;

import com.domain.Record;
import com.domain.Resulte;
import com.service.RecordService;
import com.service.ResulteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于课程成绩模块的前后端交互
 * @author : zzc
 * @version 1.1.0
 **/
@Controller
@RequestMapping("/resulte")
public class ResulteController {
    @Autowired
    private ResulteService resulteService ;
    @Autowired
    private RecordService recordService ;

    /**
     * 查询员工成绩单
     * <p>根据传入的员工id返回员工所选课程的成绩单</p>
     * <p><pre>{@code
     * 访问url: /resulte/findUserResulte
     * 访问方式: {GET,POST}
     * 请求参数：id(员工的id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return List<Resulte> 返回员工的成绩单
     */
    @ResponseBody
    @RequestMapping("/findUserResulte")
    public List<Resulte> findUserResulte(HttpServletRequest request){
        //1. 根据传入的员工uid ,查询该员工所有选择课程的成绩单
        List<Resulte> rlist = new ArrayList<>() ;
        try{
            String uid = request.getParameter("id");
            rlist = resulteService.findResultByUid(Long.valueOf(uid)) ;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rlist ;
        }
    }

    /**
     * 查询教师教授课程的成绩单
     * <p>根据传入的课程id返回该课程下所有员工该课程的成绩单</p>
     * <p><pre>{@code
     * 访问url: /resulte/findTeacherCourseResulte
     * 访问方式: {GET,POST}
     * 请求参数：id(课程的id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return List<Resulte> 返回该课程下所有员工该课程的成绩单
     */
    @ResponseBody
    @RequestMapping("/findTeacherCourseResulte")
    public List<Resulte> findTeacherCourseResulte(HttpServletRequest request){
        //1. 根据传入的课程cid ,查询该课程下员工的成绩单
        List<Resulte> rlist = new ArrayList<>() ;
        try{
            String cid = request.getParameter("id");
            rlist = resulteService.findResultByCid(Long.valueOf(cid)) ;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rlist ;
        }
    }

    /**
     * 录入员工的成绩
     * <p>根据传入的员工id和课程id以及新的分数修改课程成绩</p>
     * <p><pre>{@code
     * 访问url: /resulte/updateResulte
     * 访问方式: {GET,POST}
     * 请求参数：id(员工的id)，cid(课程的id)，grade(新成绩)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return map.("state") = 0 表示更新失败,map.("state") = 1表示更新成功
     */
    @ResponseBody
    @RequestMapping("/updateResulte")
    public HashMap updateGrade(HttpServletRequest request){
        //1. 获取课程id，员工id以及新的成绩
        HashMap s = new HashMap( ) ;
        String uid = request.getParameter("id") ;
        String cid = request.getParameter("cid") ;
        String grade = request.getParameter("grade") ;

        //2. 封装成成绩记录和选课记录
        Resulte resulte = new Resulte(Long.valueOf(uid),Long.valueOf(cid),Double.valueOf(grade));
        Record record =  recordService.findRecordByUidAndCid(Long.valueOf(uid),Long.valueOf(cid)) ;
        record.setState(1);// 录入成绩后，选课记录的成绩状态需要由0(未录入)变为1(已录入)
        s.put("state",0) ;
        if(recordService.updateRecordAndResulte(record,resulte))
            s.put("state",1) ; //更新成功，则修改状态未1，表示成功
       return s ;
    }

}
