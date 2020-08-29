package com.ygb.service;

import com.ygb.entity.AreaVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAreaService {

    AreaVo getById(int areaId);

    List<AreaVo> list(@Param("pager") PageObject pager);

    int count();

    boolean del(String[] ids);

    boolean add(AreaVo areaVo);

    boolean update(AreaVo areaVo);
}
