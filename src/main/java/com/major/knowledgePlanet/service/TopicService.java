package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Topic;
import com.major.knowledgePlanet.entity.TopicVO;

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
    List<TopicVO> getAllTopic(Long planetCode);

    /**
     * 点赞帖子
     * @param topicId 1
     * @param userId 2
     * @author Covenant
     * @date 2022-05-01 14:24
     */
    void praise(Long topicId,Long userId) throws Exception;

    /**
     * 取消点赞帖子
     * @param topicId 1
     * @param userId 2
     * @author Covenant
     * @date 2022-05-01 14:27
     */
    void unPraise(Long topicId,Long userId)throws Exception;

}
