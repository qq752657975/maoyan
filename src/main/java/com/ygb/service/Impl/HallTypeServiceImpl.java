package com.ygb.service.Impl;

import com.ygb.Dao.IHallTypeDao;
import com.ygb.entity.HallTypeVo;
import com.ygb.service.IHallTypeService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class HallTypeServiceImpl implements IHallTypeService {
    @Resource
    IHallTypeDao iHallTypeDao;


    @Override
    public int count() {
        return iHallTypeDao.count();
    }

    @Override
    public boolean del(String[] ids) {
       int num =  iHallTypeDao.del(ids);
       if(num>0){
           return true;
       }else{
           return false;
       }

    }

    @Override
    public boolean add(HallTypeVo hallType) {
        int num =  iHallTypeDao.add(hallType);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean update(HallTypeVo hallType) {
        int num =  iHallTypeDao.update(hallType);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public HallTypeVo getById(int hallTypeId) {

        return iHallTypeDao.getById(hallTypeId);
    }

    @Override
    public List<HallTypeVo> list(PageObject pageObject) {
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
        return iHallTypeDao.list(pageObject);
    }
}
