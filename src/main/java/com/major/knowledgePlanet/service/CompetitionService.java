package com.major.knowledgePlanet.service;

import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.VO.CompetitionVO;
import com.major.knowledgePlanet.VO.QuestionVO;
import com.major.knowledgePlanet.entity.Competition;
import com.major.knowledgePlanet.entity.Question;

import java.util.List;
import java.util.Map;

public interface CompetitionService {

    List<CompetitionVO> getCompetitionByPlanet(Long planetCode, Long userId);

    List<CompetitionVO> getCompetitionByPlanetStarted(Long planetCode,Long userId);

    /**
    * 添加竞赛，返回竞赛id
    * @param competition 1
    * @return : java.lang.Long
    * @author Covenant
    * @date 2022-05-12 11:55
    */
    Long addCompetition(Competition competition);

    void joinOrQuitCompetition(Long userId,Long competitionId,Integer type) throws Exception;

    void releaseOrAbolish(Long competitionId,Integer type) throws Exception;

    List<CompetitionVO>getRegisteredCompetition(Long userId);

    List<Question> getQuestion(Long competitionId);

    List<QuestionVO> getQuestionWithUserResult(Long competitionId, Long userId);

    Boolean submitAnswer(Long questionId,Long userId,String answer);

    Integer getCompetitionScore(Long competitionId,Long userId);

    void addQuestion(Question question);

    JSONObject getLeaderBoard(Long planetCode,Long userId);

    List<Question> getQuestionListWithAnswer(Long competitionId);

    void updateQuestion(Question question);


}
