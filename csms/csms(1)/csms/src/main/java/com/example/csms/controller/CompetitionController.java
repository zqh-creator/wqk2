package com.example.csms.controller;
import com.example.csms.entity.Match;
import com.example.csms.entity.Result;
import com.example.csms.service.MatchService;
import org.springframework.web.bind.annotation.*;


@RestController
public class CompetitionController {

    private final MatchService matchService;

    public CompetitionController(MatchService matchService)
    {
        this.matchService = matchService;

    }

    /*根据类型获取竞赛表开始*/
    @GetMapping("/Competition/getByType")
    public Result getMatchByType(Match match)
    {
        if(!matchService.selectByMatchType(match.getType()).isEmpty())
        {
            return Result.success(matchService.selectByMatchType(match.getType()));
        }
        else {
           return Result.success("无");
        }
    }

    @GetMapping("/Competition/getByMatchStartTime")
    public Result getMatchByStartTime(Match match)
    {
        if(!matchService.selectByMatchStartTime(match.getCompetitionStartTime()).isEmpty())
        {
            return Result.success(matchService.selectByMatchStartTime(match.getCompetitionStartTime()));
        }
        else {
            return Result.success("无");
        }
    }

    @GetMapping("/Competition/getByMatchEndTime")
    public Result getMatchByEndTime(Match match)
    {
        if(!matchService.selectByMatchEndTime(match.getCompetitionEndTime()).isEmpty())
        {
            return Result.success(matchService.selectByMatchEndTime(match.getCompetitionEndTime()));
        }
        else {
            return Result.success("无");
        }
    }

    @GetMapping("/Competition/getByRegistrationStartTime")
    public Result getMatchByRegistrationStartTime(Match match)
    {
        if(!matchService.selectByRegistrationStartTime(match.getRegistrationStartTime()).isEmpty())
        {
            return Result.success(matchService.selectByRegistrationStartTime(match.getRegistrationStartTime()));
        }
        else {
            return Result.success("无");
        }
    }

    @GetMapping("/Competition/getByRegistrationEndTime")
    public Result getMatchByRegistrationEndTime(Match match)
    {
        if(!matchService.selectByRegistrationEndTime(match.getRegistrationEndTime()).isEmpty())
        {
            return Result.success(matchService.selectByRegistrationEndTime(match.getRegistrationEndTime()));
        }
        else {
            return Result.success("无");
        }

    }
    /*根据类型返回指定竞赛表结束*/


    /*发布竞赛操作开始*/
    //发布竞赛
    @PostMapping("/Competition/savaCompetition")
    public Result saveMatch( Match match)
    {
        //需要一个以match为参数的方法保存match表，返回值任意 ok
       int i= matchService.saveMatch( match);
       if(i>0){
           return Result.success("match表保存成功");
       }
       else {
           return Result.error("保存失败");
       }
    }

    //修改竞赛表
    @PutMapping("/Competition/updateMatch")
    public Result updateMatch(Match match)
    {
        //需要一个更新match表的方法，参数为matchId，如果matchId不存在抛出异常,如果matchId存在，用参数match更新旧的记录 ok
        try {
            matchService.updateMatchById(match);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success("修改成功");
    }

    //删除竞赛表
    @DeleteMapping("/Competition/deleteMatch/{matchId}")
    public Result deleteMatch(@PathVariable("matchId") String matchId)
    {
        //需要一个以matchId为参数的方法用于删除数据库对应的记录
        if(matchService.deleteMatchRecordById(matchId))
        {
            return Result.success("删除成功");
        }
        else {
            return Result.error("没有对应记录，删除失败");
        }
    }
    /*发布竞赛操作结束*/
    }




