package com.service.impl;

import com.dao.PayDao;
import com.domain.Lecture;
import com.domain.Pay;
import com.domain.Resulte;
import com.service.LectureService;
import com.service.PayService;
import com.service.ResulteService;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 用于提供教师薪资业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.PayService
 **/
@Service("payService")
public class PayServiceImpl implements PayService {

    @Autowired
    private PayDao payDao ;
    @Autowired
    private TeacherService teacherService ;
    @Autowired
    private LectureService lectureService ;
    @Autowired
    private ResulteService resulteService ;
    @Override
    public Pay findPayByCid(long cid) {
        return payDao.findPayByCid(cid);
    }

    @Override
    public List<Pay> findAllPay() {
        return payDao.findAllPay();
    }

    @Override
    public boolean updatePay(Pay pay) {
        return payDao.updatePay(pay);
    }

    @Override
    public boolean deletePay(Pay pay) {
        return payDao.deletePay(pay);
    }

    @Override
    public boolean insertPay(Pay pay) {
        return payDao.insertPay(pay);
    }

    @Override
    public double findTeacherPay(Long tid) {
        double pay = 0 ;
        //1. 先找到老师教授的所有课程
        List<Lecture> lectures = lectureService.findLectureByTid(tid)  ;
        //2. 再统计课程的薪资 , 薪资由基础工资+合格员工薪资组成
        for(Lecture lecture : lectures){
            //2.1 找到该课程的成绩表
            pay += (double)findCoursePay(lecture.getCid()).get("pay") ;
        }
        return pay;
    }

    @Override
    public HashMap findCoursePay(long cid) {
        HashMap s = new HashMap( );
        double pay =0 ; //用于计算课程薪资，薪资=合格工资+基础工资
        int qualified = 0  ; //合格人数
        //1. 获取薪资表
        Pay pays  = payDao.findPayByCid(cid) ;
        //2. 获取员工成绩单
        List<Resulte> resulteList = resulteService.findResultByCid(cid);
        for(Resulte resulte : resulteList){
            //2.1 合格员工计算合格工资
            if(resulte.getGrade()>=60.0){
                pay += pays.getQualified();
                qualified++ ;
            }
        }
        s.put("pay",pay+pays.getBasic())  ;
        s.put("qualified",qualified) ;
        return s ;
    }


}
