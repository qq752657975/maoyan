package com.ygb.service.Impl;

import com.ygb.Dao.IManagerDao;
import com.ygb.entity.AgentInfo;
import com.ygb.entity.Manager;
import com.ygb.entity.ProvinceVo;
import com.ygb.service.IManagerService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManagerServiceImpl implements IManagerService {

    @Resource
    IManagerDao iManagerDao;

    //登录
    @Override
    public Manager login(Manager manager) {

        return iManagerDao.login(manager);


    }
    //根据条件查询总条数
    @Override
    public int count(AgentInfo agentInfo) {
        return iManagerDao.count(agentInfo);
    }

    //根据条件和分页参数查询数据
    @Override
    public List<Manager> mgrList(AgentInfo agentInfo, PageObject pageObject,Manager manager) {
        if(pageObject.getStartRow() <=1){
            pageObject.setStartRow(1);
        }
        return iManagerDao.mgrList(agentInfo,pageObject,manager);
    }


    //查询所有城市
    @Override
    public List<ProvinceVo> provinceList() {
        return iManagerDao.provinceList();
    }

    @Override
    public int updateInfo(Manager manager) {
        return iManagerDao.updateInfo(manager);
    }

    @Override
    public boolean getManagerAccount(String managerAccount) {
        if(managerAccount != null && managerAccount.length()>0){
            Manager managerAccount1 = iManagerDao.getManagerAccount(managerAccount);
            if(managerAccount1 == null){
                return true;
            }
        }
        return false;
    }

    @Override
    public Manager getById(int managerId) {
        return iManagerDao.getById(managerId);
    }

    @Override
    public int updatePwd(Manager manager) {
        return iManagerDao.updatePwd(manager);
    }

    @Override
    public List<AgentInfo> agentList(int agentId) {

        return iManagerDao.agentList(agentId);
    }

    @Override
    public boolean add(Manager manager) {
        if(manager != null){
            int flag = iManagerDao.add(manager);
            if(flag>0){
                return true;
            }else{
                return false;
            }
        }else {
               return false;
        }
    }

    @Override
    public boolean update(Manager manager) {
        if(manager != null){
            int flag = iManagerDao.update(manager);
            if(flag>0){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean del(String id) {
        if(id !=null && id != ""){
            String[] ids = id.split(",");
            int flag = iManagerDao.del(ids);
            if(flag>0){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }


}
