package com.ygb.service.Impl;

import com.ygb.Dao.IHallTypeDao;
import com.ygb.Dao.IPlayTimeDAO;
import com.ygb.entity.PlayTimeVo;
import com.ygb.service.IPlayTimeService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PlayTimeServiceImpl implements IPlayTimeService {

    @Resource
    IPlayTimeDAO iPlayTimeDAO;
    @Override
    public PlayTimeVo getById(int playTimeId) {
        if(playTimeId>0){
            return iPlayTimeDAO.getById(playTimeId);
        }
        return null;
    }

    @Override
    public List<PlayTimeVo> list(PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }

        return iPlayTimeDAO.list(pager);
    }

    @Override
    public int count() {
        return iPlayTimeDAO.count();
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int num = iPlayTimeDAO.del(ids);
            if(num>0){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean add(PlayTimeVo playTime) {
        if(playTime != null){
            int num = iPlayTimeDAO.add(playTime);
            if(num>0){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean update(PlayTimeVo playTime) {
        if(playTime != null){
            int num = iPlayTimeDAO.update(playTime);
            if(num>0){
                return true;
            }
            return false;
        }
        return false;
    }
}
