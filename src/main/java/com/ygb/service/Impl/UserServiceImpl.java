package com.ygb.service.Impl;

import com.ygb.Dao.IUserDao;
import com.ygb.entity.UserVo;
import com.ygb.service.IUserService;

import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {
    @Resource(name = "iUserDao")
    IUserDao iUserDao;

    //获取用户id
    @Transactional
    public UserVo getById(String mobile) {
       return iUserDao.getById(mobile);
    }

    public List<UserVo> list(UserVo user, PageObject pageObject){
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
        return iUserDao.list(user,pageObject);
    }

    @Override
    public int count(UserVo user) {
        return iUserDao.count(user);
    }

    @Override
    public boolean del(String[] arr) {
        if(arr != null && arr.length>0){
            int del = iUserDao.del(arr);
            if(del > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(UserVo user) {
        if(user != null){
            int add = iUserDao.add(user);
            if(add > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(UserVo area) {
        if(area != null){
            int update = iUserDao.update(area);
            if(update > 0){
                return true;
            }
        }
        return false;
    }

}
