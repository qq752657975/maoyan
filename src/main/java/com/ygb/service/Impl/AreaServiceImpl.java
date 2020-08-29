package com.ygb.service.Impl;

import com.ygb.Dao.IAreaDAO;
import com.ygb.entity.AreaVo;
import com.ygb.service.IAreaService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AreaServiceImpl implements IAreaService {
    @Resource
    IAreaDAO iAreaDAO;
    @Override
    public AreaVo getById(int areaId) {
        if(areaId>0){
            return iAreaDAO.getById(areaId);
        }
        return null;
    }

    @Override
    public List<AreaVo> list(PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return iAreaDAO.list(pager);
    }

    @Override
    public int count() {
        return iAreaDAO.count();
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int num = iAreaDAO.del(ids);
            if(num > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(AreaVo areaVo) {
        if(areaVo != null){
            int num = iAreaDAO.add(areaVo);
            if(num > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(AreaVo areaVo) {
        if(areaVo != null){
            int num = iAreaDAO.update(areaVo);
            if(num > 0){
                return true;
            }
        }
        return false;
    }
}
