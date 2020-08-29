package com.ygb.Dao;


import com.ygb.entity.SysLogVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysLogDAO {

    List<SysLogVo> list(@Param("log") SysLogVo log, @Param("pager") PageObject pager);

    int count(@Param("log") SysLogVo log);

    void add(SysLogVo sysLogVo);

}
