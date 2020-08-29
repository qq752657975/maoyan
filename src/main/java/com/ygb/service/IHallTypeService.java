package com.ygb.service;

import com.ygb.entity.HallTypeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IHallTypeService {

    HallTypeVo getById(int hallTypeId);

    List<HallTypeVo> list(@Param("pager") PageObject pager);

    int count();

    boolean del(String[] ids);

    boolean add(HallTypeVo hallType);

    boolean update(HallTypeVo hallType);
}
