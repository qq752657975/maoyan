package com.ygb.Dao;

import com.ygb.entity.HallTypeVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IHallTypeDao {

    HallTypeVo getById(int hallTypeId);

    List<HallTypeVo> list(@Param("pager") PageObject pager);

    int count();

    int del(String[] ids);

    int add(HallTypeVo hallType);

    int update(HallTypeVo hallType);
}
