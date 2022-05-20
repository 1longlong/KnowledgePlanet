package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.VO.CompetitionVO;
import com.major.knowledgePlanet.VO.LeaderBoardVO;
import com.major.knowledgePlanet.VO.QuestionVO;
import com.major.knowledgePlanet.entity.Competition;
import com.major.knowledgePlanet.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CompetitionMapper {
    /**
    * 获取星球下的所有竞赛列表，按开始时间排序，并提供当前登录用户的参与情况
    * @param planetCode 1
    * @param userId 2
    * @return : java.util.List<com.major.knowledgePlanet.VO.CompetitionVO>
    * @author Covenant
    * @date 2022-05-10 20:34
    */
    List<CompetitionVO> getCompetitionByPlanet(Long planetCode, Long userId);

    /**
    * 在用户竞赛关系表中添加一项
    * @param competitionId 1
    * @param userId 2
    * @author Covenant
    * @date 2022-05-10 21:37
    */
    void addUserCompetition(Long competitionId,Long userId);

    /**
    * 在用户竞赛关系表中删除一项
    * @param competitionId 1
    * @param userId 2
    * @author Covenant
    * @date 2022-05-10 21:38
    */
    void deleteUserCompetition(Long competitionId,Long userId);


    /**
    * 获取星球下所有还未到开始时间的竞赛列表，按开始时间由早到晚排序，提供当前用户报名情况
    * @param planetCode 1
    * @param userId 2
    * @return : java.util.List<com.major.knowledgePlanet.VO.CompetitionVO>
    * @author Covenant
    * @date 2022-05-12 11:24
    */
    List<CompetitionVO> getStartedCompetitionByPlanet(Long planetCode, Long userId);


    void addCompetition(Competition competition);


    void changeCompetitionStatus(Long competitionId,Integer status);

    List<CompetitionVO> getRegisteredCompetition(Long userId);

    List<Question> getQuestionList(Long competitionId);

    List<QuestionVO> getQuestionWithUserResult(Long competitionId,Long userId);

    Question getQuestion(Long questionId);

    void addUserQuestionRel(Long userId,Long questionId, String userAnswer,Integer getScore,Boolean isRight);

    void addUserCompetitionRel(Long userId,Long competitionId,Integer totalScore);

    Integer getSumScore(Long competitionId,Long userId);

    void addQuestion(Question question);

    List<LeaderBoardVO> getLeaderBoard(Long planetCode);

    Integer getJoinCount(Long userId,Long planetCode);


    List<Question> getQuestionListWithAnswer(Long competitionId);

    void updateQuestion(Question question);






    }
