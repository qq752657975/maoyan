package com.ygb.service;

import com.ygb.entity.PlayTimeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPlayTimeService {
    PlayTimeVo getById(int playTimeId);

    List<PlayTimeVo> list(@Param("pager") PageObject pager);

    int count();

    boolean del(String[] ids);

    boolean add(PlayTimeVo playTime);

    boolean update(PlayTimeVo playTime);
}
