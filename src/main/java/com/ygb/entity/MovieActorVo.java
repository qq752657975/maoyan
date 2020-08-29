package com.ygb.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;


//电影演员类
@Component
public class MovieActorVo implements Serializable{
    private String [] typeName={"导演","演员","副导演" ,"编剧","制片人" ,"监制","音乐","摄影师","剪辑师" ,"艺术指导","美术指导","服装师"};
    private int movieActorId; //电影演员id
    private int actorId; //演员
    private int movieId; //电影id
    private int actorType; //1.导演,2演员,3副导演 ,4编剧,5制片人 ,6监制,7音乐,8摄影师,9剪辑师 ,10艺术指导,11美术指导,12服装师,
    private String actorTypeName; //演职名称
    private String actorName; //演员名称
    private String movieName; //电影名称
    private String cimg; //演员图片

    public String getCimg() {
        return cimg;
    }

    public void setCimg(String cimg) {
        this.cimg = cimg;
    }

    public String[] getTypeName() {
        return typeName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getActorTypeName(){
        return typeName[actorType-1];
    }
    @Override
    public String toString() {
        return "编号:"+movieActorId+",演员:"+actorId+",电影:"+movieId;
    }

    public int getMovieActorId() {
        return movieActorId;
    }

    public void setMovieActorId(int movieActorId) {
        this.movieActorId = movieActorId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getActorType() {
        return actorType;
    }

    public void setActorType(int actorType) {
        this.actorType = actorType;
    }
}
