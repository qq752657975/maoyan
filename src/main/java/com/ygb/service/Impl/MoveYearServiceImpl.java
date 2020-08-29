package com.ygb.service.Impl;

import com.ygb.Dao.IMovieYearDAO;
import com.ygb.entity.MovieYearVo;
import com.ygb.service.IMoveYearService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MoveYearServiceImpl implements IMoveYearService {

    @Resource
    IMovieYearDAO iMovieYearDAO;


    @Override
    public MovieYearVo getById(int movieYearId) {
        if(movieYearId>0){
            return iMovieYearDAO.getById(movieYearId);
        }
        return null;
    }

    @Override
    public List<MovieYearVo> list(PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return iMovieYearDAO.list(pager);
    }

    @Override
    public int count() {
        return iMovieYearDAO.count();
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int mun = iMovieYearDAO.del(ids);
            if(mun > 0){
                return true;
            }
        }
            return false;
    }

    @Override
    public boolean add(MovieYearVo movieYearVo) {
        if(movieYearVo != null){
            int mun = iMovieYearDAO.add(movieYearVo);
            if(mun > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(MovieYearVo movieYearVo) {
        if(movieYearVo != null){
            int mun = iMovieYearDAO.update(movieYearVo);
            if(mun > 0){
                return true;
            }
        }
        return false;
    }

}
