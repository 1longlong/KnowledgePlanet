package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Topic;

import java.util.List;

public interface TopicService {

    /**
     *发帖功能
     *@author cj
     *@date 2022/4/21 10:48
     * @param topic
     * @param userId
     * @return Integer
     */
    Integer insertTopic (Topic topic ,Long userId);

    /**
     *获取星球下所有帖子功能
     *@author cj
     *@date 2022/4/21 12:26
     * @param planetCode
     * @return List<Topic>
     */
    List<Topic> getAllTopic(Long planetCode);
}
