package com.ygb.Dao;

import com.ygb.entity.ForumVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumDAO {

    ForumVo getById(int forumId);

    List<ForumVo> list(@Param("movieId") int movieId, @Param("pager") PageObject pager);

    int count(@Param("movieId") int movieId);

    int del(String[] ids);

    int add(ForumVo forum);

    int update(ForumVo forum);

    int updMovieScore(@Param("movieId") int movieId);

}
