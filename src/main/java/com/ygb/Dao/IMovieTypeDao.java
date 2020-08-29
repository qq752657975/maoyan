package com.ygb.Dao;

import com.ygb.entity.MovieTypeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovieTypeDao {

    MovieTypeVo getById(int movieTypeId);

    List<MovieTypeVo> list(@Param("pager") PageObject pager);

    int count();

    int del(String[] ids);

    int add(MovieTypeVo movieType);

    int update(MovieTypeVo movieType);
}
