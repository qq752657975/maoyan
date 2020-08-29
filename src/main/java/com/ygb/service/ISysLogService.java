package com.ygb.service;

import com.ygb.entity.SysLogVo;
import com.ygb.util.PageObject;

import java.util.List;

public interface ISysLogService {

    List<SysLogVo> list(SysLogVo log,PageObject pager);

    int count(SysLogVo log);

    void add(int logType,String content);
}
