package com.ygb.service;

import com.ygb.entity.ActorVo;
import com.ygb.util.PageObject;


import java.util.List;

public interface IActorService {

   ActorVo getById(int actorId);

    List<ActorVo> list(PageObject pager);

    int count();

    boolean del(String[] arr);

    boolean add(ActorVo actor);

    boolean update(ActorVo actor);
}
