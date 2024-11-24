package com.example.csms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csms.entity.Match;
import com.example.csms.entity.Teacher;
import com.example.csms.mapper.MatchMapper;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchService {

    List<Match> selectByMatchType(String matchType);
    List<Match> selectByMatchStartTime(String matchStartTime);
    List<Match> selectByMatchEndTime(String matchEndTime);
    List<Match> selectByRegistrationStartTime(String registrationStartTime);
    List<Match> selectByRegistrationEndTime(String registrationEndTime);

    Match selectByMatchId(String matchId);

    int saveMatch(Match match);

    void updateMatchById(Match match) throws Exception;

    boolean deleteMatchRecordById(String id);
}
