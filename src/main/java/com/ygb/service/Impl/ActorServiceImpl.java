package com.ygb.service.Impl;

import com.ygb.Dao.ActorDAO;
import com.ygb.entity.ActorVo;
import com.ygb.service.IActorService;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {

    @Resource
    ActorDAO actorDAO;

    public ActorVo getById(int actorId){
        return actorDAO.getById(actorId);
    }


    public  List<ActorVo> list(PageObject pager){
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return actorDAO.list(pager);
    }

    public int count(){
        return actorDAO.count();
    }

    public boolean del(String[] arr){
        if (arr != null && arr.length>0){
            int del = actorDAO.del(arr);
            if(del > 0){
                return true;
            }
        }
        return false;
    }

    public boolean add(ActorVo actor){
        if(actor != null){
            int add = actorDAO.add(actor);
            if(add > 0){
                return true;
            }
        }
        return false;
    }

    public boolean update(ActorVo actor){
        if(actor != null){
            int update = actorDAO.update(actor);
            if(update > 0){
                return true;
            }
        }
        return false;



    }
}
