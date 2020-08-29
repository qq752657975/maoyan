package com.ygb.service;

import com.ygb.entity.ForumVo;
import com.ygb.util.PageObject;

import java.util.List;

public interface IForumSerivce {

    ForumVo getById(int forumId);

    List<ForumVo> list(int movieId, PageObject pager);

    int count(int movieId);

    boolean del(String[] arr);

    boolean add(ForumVo forum);

    boolean update(ForumVo forum);
}
