package com.ygb.service;

import com.ygb.entity.MovieYearVo;
import com.ygb.entity.PlayTimeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMoveYearService {

    MovieYearVo getById(int movieYearId);

    List<MovieYearVo> list(@Param("pager") PageObject pager);


    int count();


    boolean del(String[] ids);


    boolean add(MovieYearVo movieYear);


    boolean update(MovieYearVo movieYear);
}
