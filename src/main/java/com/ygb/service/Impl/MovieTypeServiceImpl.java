package com.ygb.service.Impl;

import com.ygb.Dao.IMovieTypeDao;
import com.ygb.entity.MovieTypeVo;
import com.ygb.service.IMovieTypeService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieTypeServiceImpl implements IMovieTypeService {

    @Resource
    IMovieTypeDao iMovieTypeDao;
    @Override
    public MovieTypeVo getById(int movieTypeId) {
        if(movieTypeId>0){
           return iMovieTypeDao.getById(movieTypeId);
        }else{
            return null;
        }

    }

    @Override
    public List<MovieTypeVo> list(PageObject pager) {
        if(pager.getStartRow() <=1){
            pager.setStartRow(1);
        }
        return iMovieTypeDao.list(pager);
    }

    @Override
    public int count() {
        return iMovieTypeDao.count();
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int num = iMovieTypeDao.del(ids);
                if(num > 0){
                    return true;
                }else{
                    return false;
                }
            }else {
            return false;
        }
    }

    @Override
    public boolean add(MovieTypeVo movieType) {
        if(movieType != null){
            int num = iMovieTypeDao.add(movieType);
            if(num > 0){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean update(MovieTypeVo movieType) {
        if(movieType != null){
            int num = iMovieTypeDao.update(movieType);
            if(num > 0){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
}
