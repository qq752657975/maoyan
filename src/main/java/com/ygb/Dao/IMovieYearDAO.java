package com.ygb.Dao;


import com.ygb.entity.MovieYearVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMovieYearDAO {

    MovieYearVo getById(int movieYearId);

    List<MovieYearVo> list(@Param("pager") PageObject pager);


    int count();


    int del(String[] ids);


    int add(MovieYearVo movieYear);


    int update(MovieYearVo movieYear);

}
