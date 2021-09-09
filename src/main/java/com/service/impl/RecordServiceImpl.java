package com.service.impl;

import com.domain.Course;
import com.domain.Record;
import com.domain.Resulte;
import com.service.CourseService;
import com.service.RecordService;
import com.dao.RecordDao;
import com.service.ResulteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 用于提供选课业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.RecordService
 **/
@Service("recordService")
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordDao recordDao ;
    @Autowired
    private ResulteService resulteService ;
    @Autowired
    private CourseService courseService ;
    @Override
    public List<Record> findRecordByUid(long uid) {
        return recordDao.findRecordByUid(uid);
    }

    @Override
    public List<Record> findRecordByCid(long cid) {
        return recordDao.findRecordByCid(cid);
    }

    @Override
    public List<Record> findRecord() {
        return recordDao.findRecord();
    }

    @Override
    public boolean inserRecord(Record record) {
        return recordDao.insertRecord(record);
    }

    @Override
    public boolean updateRecord(Record record) {
        return recordDao.updateRecord(record);
    }

    @Override
    public boolean deleteRecord(Record record) {
        return recordDao.deleteRecord(record);
    }

    @Override
    public boolean updateRecordAndResulte(Record record, Resulte resulte) {
        boolean flag = false ;
        try{
            recordDao.updateRecord(record) ;
            resulteService.updateResult(resulte);
            flag = true;
        }finally {
            return flag ;
        }
    }

    @Override
    public boolean deleteRecordAndResulte(Record record, Resulte resulte) {
        boolean flag = false ;
        try{
            recordDao.deleteRecord( record);
            resulteService.deleteResult(resulte);
            flag = true;
        }finally {
            return flag ;
        }
    }

    @Override
    public HashMap insertStaffRecord(long cid, long uid) {
        HashMap s = new HashMap() ;
        s.put("state",0) ;

        //0. 判断人数是否满
        Course course = courseService.findCourse(Long.valueOf(cid)) ;
        int num = course.getNum() ;
        int selectStaff = recordDao.findRecordByCid(Long.valueOf(cid)).size() ;
        if(selectStaff >= num){
            s.put("state",2) ;
            return  s ;
        }

        //1. 增加选课记录
        Record record = new Record();
        record.setUid(uid);
        record.setCid(cid);
        record.setState(0);
        inserRecord(record) ;

        //2. 增加成绩记录
        Resulte resulte = new Resulte(uid,cid,0) ;
        resulteService.inserResult(resulte) ;

        //3. 返回执行结果
        s.put("state",1);
        return s ;
    }

    @Override
    public Record findRecordByUidAndCid(long uid, long cid) {
        return recordDao.findRecordByUidAndCid(uid,cid);
    }
}
