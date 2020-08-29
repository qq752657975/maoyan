package com.ygb.Dao;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.Manager;
import com.ygb.entity.ProvinceVo;
import com.ygb.entity.UserVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("iUserDao")
public interface IUserDao {


    UserVo getById(String mobile);

    List<UserVo> list(@Param("user") UserVo user,@Param("pager") PageObject pager);

    int count(@Param("user") UserVo user);

    int del(String[] ids);

    int add(UserVo user);

    int update(UserVo user);


}
