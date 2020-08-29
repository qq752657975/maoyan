package com.ygb.Dao;

import com.ygb.entity.AgentInfo;
import com.ygb.entity.HallSeatVo;
import com.ygb.entity.HallTypeVo;
import com.ygb.entity.MovieHallVo;
import com.ygb.util.PageObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMovieHallDao {

    MovieHallVo getById(int movieHallId);
    List<MovieHallVo> list(@Param("movieHall") MovieHallVo movieHall, @Param("pager") PageObject pager);
    int count(@Param("movieHall") MovieHallVo movieHall);
    int del(String[] ids);
    int add(MovieHallVo movieHall);
    int update(MovieHallVo movieHall);
    List<AgentInfo> agentList(@Param("agentId") int agentId);
    List<HallTypeVo> hallTypeList();

    int seatCount(MovieHallVo movieHall);
    List<HallSeatVo> getSeatByHallId(int hallId);
    int seatDel(int hallId);
    int seatAdd(HallSeatVo hallSeat);
    int seatUpdate(HallSeatVo hallSeat);
    int updateHallSeat(MovieHallVo movieHall);
}
