package com.ygb.Dao;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.Manager;
import com.ygb.entity.ProvinceVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IManagerDao {
    Manager login(@Param("mgr") Manager manager);

    Manager getManagerAccount(String managerAccount);

    int count(@Param("agent") AgentInfo agentInfo);

    List<ProvinceVo> provinceList();

    List<Manager> mgrList(@Param("agent")AgentInfo agentInfo,@Param("pager") PageObject pageObject,@Param("mgr") Manager manager);

    int updateInfo(Manager manager);

    Manager getById(int managerId);

    int updatePwd(Manager manager);

    List<AgentInfo> agentList(int agentId);

    int add(Manager manager);

    int update(Manager manager);

    int del(String[] ids);

}
