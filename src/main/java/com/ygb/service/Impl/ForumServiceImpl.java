package com.ygb.service.Impl;

import com.ygb.Dao.ForumDAO;
import com.ygb.entity.ForumVo;
import com.ygb.service.IForumSerivce;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ForumServiceImpl implements IForumSerivce {

    @Resource
    ForumDAO forumDAO;

    public ForumVo getById(int forumId){
        return forumDAO.getById(forumId);
    }

    public List<ForumVo> list(int movieId, PageObject pager){
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return forumDAO.list(movieId,pager);
    }

    public int count(int movieId){
        return forumDAO.count(movieId);
    }

    public boolean del(String[] arr){
        if(arr != null && arr.length>0){
            int del = forumDAO.del(arr);
            if(del >0){
                return true;
            }
        }
       return false;

    }

    @Transactional
    public boolean add(ForumVo forum){
        if(forum != null){
            int num = forumDAO.add(forum);
            //更新电影平均分
            int num1 = forumDAO.updMovieScore(forum.getMovieId());
            if(num1>0 && num1>0){
                return true;
            }
        }
        return false;
    }

    public boolean update(ForumVo forum){
        if(forum != null){
            int update = forumDAO.update(forum);
            if(update >0){
                return true;
            }
        }
        return false;
    }
}
