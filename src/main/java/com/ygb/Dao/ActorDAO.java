package com.ygb.Dao;


import com.ygb.entity.ActorVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorDAO {

    ActorVo getById(int actorId);
    List<ActorVo> list(@Param("pager") PageObject pager);
    int count();
    int del(String[] ids);
    int add(ActorVo actor);
    int update(ActorVo actor);

}
