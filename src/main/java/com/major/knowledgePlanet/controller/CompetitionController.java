package
        com.major.knowledgePlanet.controller;

import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.VO.CompetitionVO;
import com.major.knowledgePlanet.VO.QuestionVO;
import com.major.knowledgePlanet.constValue.IntegralEnum;
import com.major.knowledgePlanet.entity.Competition;
import com.major.knowledgePlanet.entity.Question;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.CompetitionService;
import com.major.knowledgePlanet.service.PlanetService;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * TODO:此处写CompetitionController类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/10 20:20
 */
@RestController
public class CompetitionController {

    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="competitionServiceImpl")
    private CompetitionService competitionService;

    @Resource(name="planetServiceImpl")
    private PlanetService planetService;

    @GetMapping("competition/getCompetitionByPlanet")
    @ApiOperation(value="获取星球中所有竞赛，按开始时间由早到晚排序")
    public Response getCompetitionByPlanet(HttpServletRequest request, @RequestParam("planetCode")Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        List<CompetitionVO> competitionByPlanet = competitionService.getCompetitionByPlanet(planetCode, userId);
        return Response.success().data("competitionList",competitionByPlanet);
    }

    @PostMapping("competition/joinOrQuitCompetition")
    @ApiOperation(value="参加或退出竞赛")
    public Response joinOrQuitCompetition(HttpServletRequest request,@RequestParam("competitionId")Long competitionId,@RequestParam("type")Integer type){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        try {
            competitionService.joinOrQuitCompetition(userId,competitionId,type);
            return Response.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.clientError().message(e.getMessage());
        }
    }

    @PostMapping("competition/releaseOrAbolish")
    @ApiOperation(value="发布或取消竞赛")
    public Response releaseOrAbolish(@RequestParam("competitionId")Long competitionId,@RequestParam("type")Integer type){
        try {
            competitionService.releaseOrAbolish(competitionId,type);
            return Response.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.clientError().message(e.getMessage());
        }
    }
    @PostMapping("competition/addCompetition")
    @ApiOperation(value="添加竞赛")
    public Response addCompetition(@RequestBody Competition competition){
        Long competitionId = competitionService.addCompetition(competition);
        return Response.success().data("competitionId",competitionId);
    }

    @PostMapping("competition/addQuestion")
    @ApiOperation(value="添加问题")
    public Response addQuestion(@RequestBody Question question){
        competitionService.addQuestion(question);
        return Response.success();
    }


    @GetMapping("competition/getCompetitionStarted/{planetCode}")
    @ApiOperation(value="获取进行中的竞赛")
    public Response getCompetitionStarted(HttpServletRequest request,@PathVariable("planetCode")Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        List<CompetitionVO> competitionList = competitionService.getCompetitionByPlanetStarted(planetCode, userId);
        return Response.success().data("competitionList",competitionList);
    }


    @GetMapping("competition/getRegisteredCompetition")
    @ApiOperation(value="获取参赛记录")
    public Response getRegisteredCompetition(HttpServletRequest request){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        List<CompetitionVO> registeredCompetition = competitionService.getRegisteredCompetition(userId);
        return Response.success().data("competitionList",registeredCompetition);
    }

    @GetMapping("competition/getQuestionWithUserResult/{competitionId}")
    @ApiOperation(value="查看参赛题目和自己的答案")
    public Response getQuestionWithUserResult(HttpServletRequest request,@PathVariable("competitionId")Long competitionId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        List<QuestionVO> questionWithUserResult = competitionService.getQuestionWithUserResult(competitionId, userId);
        return Response.success().data("questionList",questionWithUserResult);
    }


    @GetMapping("competition/getQuestion")
    @ApiOperation(value="获取题目列表")
    public Response getQuestion(@RequestParam("competitionId")Long competitionId){
        List<Question> question = competitionService.getQuestion(competitionId);
        return Response.success().data("question",question);
    }


    @PostMapping("competition/submitAnswer")
    @ApiOperation(value="提交问题答案")
    public Response submitAnswer(HttpServletRequest request,@RequestParam("questionId")Long questionId,@RequestParam("answer")String answer){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        Boolean result = competitionService.submitAnswer(questionId, userId, answer);
        return Response.success().data("result",result);
    }

    @PostMapping("competition/getCompetitionScore")
    @ApiOperation(value="获取竞赛成绩")
    public Response getCompetitionScore(HttpServletRequest request,@RequestParam("competitionId")Long competitionId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        Integer score = competitionService.getCompetitionScore(competitionId, userId);
        return Response.success().data("score",score);
    }


    @GetMapping("competition/getLeaderBoard")
    @ApiOperation(value="按参加竞赛次数返回排行榜和自己的参与次数")
    public Response getLeaderBoard(HttpServletRequest request,@RequestParam("planetCode")Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        JSONObject obj = competitionService.getLeaderBoard(planetCode, userId);
        return Response.success().data("result",obj);
    }

    @GetMapping("competition/getQuestionListWithAnswer")
    @ApiOperation(value="获取带有答案的问题列表")
    public Response getQuestionListWithAnswer(@RequestParam("competitionId")Long competitionId){
        List<Question> questionList = competitionService.getQuestionListWithAnswer(competitionId);
        return Response.success().data("questionList",questionList);
    }

    @PostMapping("competition/updateQuestion")
    @ApiOperation(value="修改问题")
    public Response updateQuestion(@RequestBody Question question){
        competitionService.updateQuestion(question);
        return Response.success();
    }


    @PostMapping("competition/submitAnswers")
    @ApiOperation(value="提交竞赛答案")
    public Response submitAnswers(HttpServletRequest request, @RequestBody Map<Long,String>answers,@RequestParam("planetCode")Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            competitionService.submitAnswer(entry.getKey(),userId,entry.getValue());
        }
        planetService.changeUserPlanetIntegral(userId,planetCode, IntegralEnum.JOIN_COMPETITION_INTEGRAL.getIntegral());
        return Response.success();
    }


}
