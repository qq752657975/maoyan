package com.ygb.service;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.Manager;
import com.ygb.entity.ProvinceVo;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IManagerService {

    Manager login(Manager manager);

    int count(AgentInfo agentInfo);

    List<Manager> mgrList(AgentInfo agentInfo, PageObject pageObject,Manager manager);

    List<ProvinceVo> provinceList();

    int updateInfo(Manager manager);

    boolean getManagerAccount(String managerAccount);
    Manager getById(int managerId);

    int updatePwd(Manager manager);

    List<AgentInfo> agentList(int agentId);

    boolean add(Manager manager);

    boolean update(Manager manager);

    boolean del(String ids);

}
