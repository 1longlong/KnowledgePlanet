package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.VO.CompetitionVO;
import com.major.knowledgePlanet.VO.QuestionVO;
import com.major.knowledgePlanet.entity.Competition;
import com.major.knowledgePlanet.entity.Question;
import com.major.knowledgePlanet.mapper.CompetitionMapper;
import com.major.knowledgePlanet.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 竞赛服务层
 *
 * @author 孟繁霖
 * @date 2022/5/8 15:18
 */
@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;


    @Override
    public List<CompetitionVO> getCompetitionByPlanet(Long planetCode, Long userId) {
        return competitionMapper.getCompetitionByPlanet(planetCode,userId);
    }

    @Override
    public List<CompetitionVO> getCompetitionByPlanetNotStart(Long planetCode, Long userId) {
        return competitionMapper.getCompetitionByPlanetNotStart(planetCode,userId);
    }

    @Override
    public Long addCompetition(Competition competition) {
        competition.setCreateTime(new Date());
        //设置初始为未发布
        competition.setStatus(0);
        competitionMapper.addCompetition(competition);
        return competition.getCompetitionId();
    }

    @Override
    public void joinOrQuitCompetition(Long userId, Long competitionId,Integer type) throws Exception {
        if(type==1){
            competitionMapper.addUserCompetition(competitionId,userId);
        }else if(type==0){
            competitionMapper.deleteUserCompetition(competitionId,userId);
        }else{
            throw new Exception("参数错误");
        }
    }

    @Override
    public void releaseOrAbolish(Long competitionId, Integer type) throws Exception {
        if(type==1){
            competitionMapper.changeCompetitionStatus(competitionId,1);
        }else if(type==2){
            competitionMapper.changeCompetitionStatus(competitionId,2);
        }else{
            throw new Exception("参数错误");
        }
    }

    @Override
    public List<CompetitionVO> getRegisteredCompetition(Long userId) {
        return competitionMapper.getRegisteredCompetition(userId);
    }

    @Override
    public List<Question> getQuestion(Long competitionId) {
        return competitionMapper.getQuestionList(competitionId);
    }

    @Override
    public List<QuestionVO> getQuestionWithUserResult(Long competitionId, Long userId) {
        return competitionMapper.getQuestionWithUserResult(competitionId,userId);
    }

    @Override
    public Boolean submitAnswer(Long questionId, Long userId, String answer) {
        Question question = competitionMapper.getQuestion(questionId);
        if(question.getAnswer().equals(answer)){
            competitionMapper.addUserQuestionRel(userId,questionId,answer,question.getScore(),true);
            return true;
        }else{
            competitionMapper.addUserQuestionRel(userId,questionId,answer,0,false);
            return false;
        }
    }

    @Override
    public Integer getCompetitionScore(Long competitionId, Long userId) {
        //计算总结果
        Integer sumScore = competitionMapper.getSumScore(competitionId, userId);
        //更新结果
        competitionMapper.addUserCompetitionRel(userId,competitionId,sumScore);
        //返回成绩
        return sumScore;
    }

    @Override
    public void addQuestion(Question question) {
        competitionMapper.addQuestion(question);
    }
}
