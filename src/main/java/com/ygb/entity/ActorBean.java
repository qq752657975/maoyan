package com.ygb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//演员类别
public class ActorBean implements Serializable {
    private int actorTypeId;  //演员类别编号
    private String actorTypeName;  //演员类型名称
    private List<MovieActorVo> actorList = new ArrayList<MovieActorVo>();
    public int getActorTypeId() {
        return actorTypeId;
    }

    public void setActorTypeId(int actorTypeId) {
        this.actorTypeId = actorTypeId;
    }

    public String getActorTypeName() {
        return actorTypeName;
    }

    public void setActorTypeName(String actorTypeName) {
        this.actorTypeName = actorTypeName;
    }

    public List<MovieActorVo> getActorList() {
        return actorList;
    }

    public void setActorList(List<MovieActorVo> actorList) {
        this.actorList = actorList;
    }
}
