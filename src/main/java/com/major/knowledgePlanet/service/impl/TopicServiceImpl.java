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
}
