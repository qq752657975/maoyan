package com.ygb.Dao;


import com.ygb.entity.AreaVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAreaDAO {

    AreaVo getById(int areaId);

    List<AreaVo> list(@Param("pager") PageObject pager);

    int count();

    int del(String[] ids);

    int add(AreaVo areaVo);

    int update(AreaVo areaVo);

}
