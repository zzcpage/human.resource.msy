package com.service.impl;

import com.dao.ResulteDao;

import com.domain.Resulte;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.ResulteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用于提供课程成绩业务逻辑操作
 * @author : zzc
 * @version 1.1.0
 * @see com.service.ResulteService
 **/
@Service(value = "resulteService")
public class ResulteServiceImpl implements ResulteService {

    @Autowired
    private ResulteDao resultDao;

    @Override
    public Resulte findResulte(long uid, long cid) {
        return resultDao.findResulte(uid,cid);
    }

    @Override
    public List<Resulte> findResultByUid(long uid) {
        return resultDao.findResultByUid(uid);
    }

    @Override
    public List<Resulte> findResultByCid(long cid) {
        return resultDao.findResultByCid(cid);
    }



    @Override
    public boolean inserResult(Resulte result) {
        return resultDao.insertResult(result);
    }

    @Override
    public boolean updateResult(Resulte result) {
        return resultDao.updateResult(result);
    }

    @Override
    public boolean deleteResult(Resulte result) {
        return resultDao.deleteResult(result);
    }


    @Override
    public List<Resulte> findResulteLimit(int offset, int pageSize, Long valueOf) {
        return resultDao.findStaffResulteLimit(offset,pageSize,valueOf);
    }

    @Override
    public List<Resulte> findAllResulteSearch(  String search, Long valueOf) {
        return resultDao.findAllStaffResulteSearch( search,valueOf);
    }

    @Override
    public List<Resulte> findResulteLimitAndSearch(int offset, int pageSize, String search, Long valueOf) {
        return resultDao.findStaffResulteLimitAndSearch(offset,pageSize,search,valueOf);
    }
}
