package com.ygb.Dao;


import com.ygb.entity.PlayTimeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IPlayTimeDAO {

    PlayTimeVo getById(int playTimeId);

    List<PlayTimeVo> list(@Param("pager") PageObject pager);

    int count();

    int del(String[] ids);

    int add(PlayTimeVo playTime);

    int update(PlayTimeVo playTime);

}
