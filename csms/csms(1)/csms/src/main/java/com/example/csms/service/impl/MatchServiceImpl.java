package com.example.csms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.Match;
import com.example.csms.entity.Teacher;
import com.example.csms.mapper.MatchMapper;
import com.example.csms.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchMapper matchMapper;

    //获得Match表方法开始
    public List<Match> selectByMatchType(String matchType)
    {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", matchType);
        return matchMapper.selectList(queryWrapper);
    }
    public List<Match> selectByMatchStartTime(String matchStartTime)
    {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_start_time", matchStartTime);
        if (matchMapper.selectList(queryWrapper).isEmpty()) {
            System.out.println("没有对应记录");
        }
        return matchMapper.selectList(queryWrapper);
    }
    public List<Match> selectByMatchEndTime(String matchEndTime)
    {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_end_time", matchEndTime);
        if (matchMapper.selectList(queryWrapper).isEmpty()) {
            System.out.println("没有对应记录");
        }
        return matchMapper.selectList(queryWrapper);
    }
    public List<Match> selectByRegistrationStartTime(String registrationStartTime)
    {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("registration_start_time", registrationStartTime);
        if (matchMapper.selectList(queryWrapper).isEmpty()) {
            System.out.println("没有对应记录");
        }
        return matchMapper.selectList(queryWrapper);
    }
    public List<Match> selectByRegistrationEndTime(String registrationEndTime)
    {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("registration_end_time", registrationEndTime);
        if (matchMapper.selectList(queryWrapper).isEmpty()) {
            System.out.println("没有对应记录");
        }
        return matchMapper.selectList(queryWrapper);
    }

    //获得Match表方法结束

    //保存比赛信息
    @Override
    public int saveMatch(Match match){
        return matchMapper.insert(match);
    }

    //根据比赛id更新比赛信息
    @Override
    public void updateMatchById(Match match) throws Exception{
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", match.getId());

        // 先查询该ID对应的记录是否存在
        Match oldMatch = matchMapper.selectOne(queryWrapper);
        if (oldMatch == null) {
            throw new Exception("要更新的比赛信息不存在，Id: " + match.getId());

        }
        else {
            // 如果存在，则执行更新操作(这里存入match会自动提取它的主码)
            matchMapper.updateById(match);
        }
    }

    //根据比赛id删除其相关信息
    @Override
    public boolean deleteMatchRecordById(String id){
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        int deleteResult = matchMapper.delete(queryWrapper);
        return deleteResult > 0;
    }

    @Override
    public Match selectByMatchId(String matchId){
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", matchId);
        return matchMapper.selectOne(queryWrapper);
    }
}