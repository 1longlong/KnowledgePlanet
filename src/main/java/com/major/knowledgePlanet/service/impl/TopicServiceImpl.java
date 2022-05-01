package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Topic;
import com.major.knowledgePlanet.entity.TopicVO;
import com.major.knowledgePlanet.mapper.TopicMapper;
import com.major.knowledgePlanet.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public Integer insertTopic(Topic topic ,Long userId){
        topic.setUserId(userId);
        topic.setTime(new Date());
        return topicMapper.insertTopic(topic);
    }

    @Override
    public List<TopicVO> getAllTopic(Long planetCode){return topicMapper.getAllTopic(planetCode);}

    @Override
    public void praise(Long topicId, Long userId) throws Exception {
        try {
            topicMapper.changeTopicUserRel(topicId, userId, true);
            topicMapper.changePraiseCount(topicId, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void unPraise(Long topicId, Long userId) throws Exception {
        try {
            topicMapper.changeTopicUserRel(topicId,userId,false);
            topicMapper.changePraiseCount(topicId,-1);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
