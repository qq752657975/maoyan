package com.ygb.service.Impl;

import com.ygb.Dao.IMovieHallDao;
import com.ygb.entity.AgentInfo;
import com.ygb.entity.HallSeatVo;
import com.ygb.entity.HallTypeVo;
import com.ygb.entity.MovieHallVo;
import com.ygb.service.IMovieHallService;
import com.ygb.util.PageObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class MovieHallServiceImpl implements IMovieHallService {

    @Resource
    IMovieHallDao iMovieHallDao;

    @Override
    public MovieHallVo getById(int movieHallId) {
        if(movieHallId >0){
            return iMovieHallDao.getById(movieHallId);
        }
        return null;
    }

    @Override
    public List<MovieHallVo> list(MovieHallVo movieHall, PageObject pager) {

        if(movieHall != null){
            if(pager.getStartRow() <=1){
                pager.setStartRow(1);
            }
           return iMovieHallDao.list(movieHall, pager);
        }
        return null;
    }

    @Override
    public int count(MovieHallVo movieHall) {
        if(movieHall != null){
            return iMovieHallDao.count(movieHall);
        }
        return 0;
    }

    @Override
    public boolean del(String[] ids) {
        if(ids != null && ids.length>0){
            int num = iMovieHallDao.del(ids);
            if(num >0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(MovieHallVo movieHall) {
       if(movieHall != null){
           int add = iMovieHallDao.add(movieHall);
           if(add > 0){
               return true;
           }
       }
       return false;
    }

    @Override
    public boolean update(MovieHallVo movieHall) {
        if(movieHall != null){
            int add = iMovieHallDao.update(movieHall);
            if(add > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AgentInfo> agentList(int agentId) {
       return iMovieHallDao.agentList(agentId);
    }

    @Override
    public List<HallTypeVo> hallTypeList() {
        return iMovieHallDao.hallTypeList();
    }

    @Override
    public int seatCount(MovieHallVo movieHall) {
         return iMovieHallDao.seatCount(movieHall);

    }

    @Override
    public List<HallSeatVo> getSeatByHallId(int hallId) {
        return iMovieHallDao.getSeatByHallId(hallId);
    }

    @Override
    public void seatDel(int hallId) {
         iMovieHallDao.seatDel(hallId);
    }

    @Override
    public void seatAdd(int movieHallId) {
        //获得该id影院的信息
        MovieHallVo hall = iMovieHallDao.getById(movieHallId);
        int row = hall.getRow();
        int col = hall.getCol();
        //生成影院的座位行和列
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                HallSeatVo hallSeat = new HallSeatVo();
                hallSeat.setHallId(movieHallId);
                hallSeat.setSeatName((i+1)+"排"+(j+1) + "座");
                hallSeat.setState(1);
                iMovieHallDao.seatAdd(hallSeat);
            }
        }
        hall.setSeatNum(hall.getRow()*hall.getCol());
        iMovieHallDao.updateHallSeat(hall);
    }
    @Transactional
    @Override
    public boolean seatUpdate(HallSeatVo hallSeat) {
        //更新座位状态
       int num = iMovieHallDao.seatUpdate(hallSeat);
       //修改可用的总座位数
       int num1 = updateHallSeat(hallSeat.getHallId());
        if(num > 0 && num1 >0){
            return true;
        }
        return false;
    }

    @Override
    public int updateHallSeat(int movieHallId) {

        MovieHallVo hall = new MovieHallVo();
        hall.setHallId(movieHallId);
        hall.setState(1);
        //获取总座位数
        int count =iMovieHallDao.seatCount(hall);

        hall.setSeatNum(count);
        //将查询的总座位数,设置到影院的总座位数
       return iMovieHallDao.updateHallSeat(hall);
    }
}
