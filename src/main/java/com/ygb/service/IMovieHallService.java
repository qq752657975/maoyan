package com.ygb.service;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.HallSeatVo;
import com.ygb.entity.HallTypeVo;
import com.ygb.entity.MovieHallVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMovieHallService {
    MovieHallVo getById(int movieHallId);
    List<MovieHallVo> list(@Param("movieHall") MovieHallVo movieHall, @Param("pager") PageObject pager);
    int count(@Param("movieHall") MovieHallVo movieHall);
    boolean del(String[] ids);
    boolean add(MovieHallVo movieHall);
    boolean update(MovieHallVo movieHall);
    List<AgentInfo> agentList(@Param("agentId") int agentId);
    List<HallTypeVo> hallTypeList();

    int seatCount(MovieHallVo movieHall);
    List<HallSeatVo> getSeatByHallId(int hallId);
    void seatDel(int hallId);
    void seatAdd(int movieHallId);
    boolean seatUpdate(HallSeatVo hallSeat);
    int updateHallSeat(int movieHallId);
}
