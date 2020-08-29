package com.ygb.entity;

import com.ygb.entity.AgentInfo;
import org.springframework.stereotype.Component;

import java.io.Serializable;


//管理员
@Component
public class Manager implements Serializable {

	private int managerId; //id
	private String managerAccount; //管理员账号
	private String managerPassword; //管理员密码
	private int managerType; //管理员类别
	private String managerName; //管理员姓名
	private Integer sex; //管理员性别
	private String contact; //联系电话
	private int agentId; //影院id
	private int invalid; //管理员状态
	private String oldpwd;
	private AgentInfo agent;
	private String img; //头像



	public AgentInfo getAgent() {
		return agent;
	}
	public void setAgent(AgentInfo agent) {
		this.agent = agent;
	}
	@Override
	public String toString() {
		return "id:"+managerId+",登录账号:"+managerAccount+",管理员:"+managerName+",管理员类别:"+managerType+",性别:"+sex+",联系人:"+contact+",影院id:"+agentId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}


	public int getInvalid() {
		return invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public int getManagerId() {
		return this.managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getManagerAccount() {
		return this.managerAccount;
	}

	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}

	public String getManagerPassword() {
		return this.managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public int getManagerType() {
		return this.managerType;
	}

	public void setManagerType(Integer managerType) {
		this.managerType = managerType;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}


}