package com.ygb.service;

import com.ygb.entity.UserVo;
import com.ygb.util.PageObject;

import java.util.List;

public interface IUserService {
     UserVo getById(String mobile);

     public List<UserVo> list(UserVo user, PageObject pageObject);

     int count(UserVo user);

     boolean del(String[] arr);

     boolean add(UserVo user);

     boolean update(UserVo area);


}
