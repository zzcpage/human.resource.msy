package com.service.impl;

import com.dao.LectureDao;
import com.domain.Lecture;
import com.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用于提供教师授课业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.LectureService
 **/
@Service(value = "lectureService")
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureDao lectureDao ;

    @Override
    public List<Lecture> findLectureByTid(long tid) {
        return lectureDao.findLectureByTid(tid);
    }

    @Override
    public Lecture findLectureByCid(long cid) {
        return lectureDao.findLectureByCid(cid);
    }

    @Override
    public List<Lecture> findLecture() {
        return lectureDao.findLecture();
    }

    @Override
    public boolean inserLecture(Lecture lecture) {
        return lectureDao.insertLecture(lecture);
    }

    @Override
    public boolean updateLecture(Lecture lecture) {
        return lectureDao.updateLecture(lecture);
    }


    @Override
    public boolean deleteLectureByCid(Lecture lecture) {
        return lectureDao.deleteLectureByCid(lecture);
    }
}
