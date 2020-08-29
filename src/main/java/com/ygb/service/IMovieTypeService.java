package com.ygb.service;

import com.ygb.entity.MovieTypeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMovieTypeService {

    MovieTypeVo getById(int movieTypeId);

    List<MovieTypeVo> list(@Param("pager") PageObject pager);

    int count();

    boolean del(String[] ids);

    boolean add(MovieTypeVo movieType);

    boolean update(MovieTypeVo movieType);
}
