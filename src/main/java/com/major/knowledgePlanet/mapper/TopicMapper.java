package com.major.knowledgePlanet.mapper;


import com.major.knowledgePlanet.entity.Topic;
import com.major.knowledgePlanet.entity.TopicVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TopicMapper {

    /**
     *发帖功能
     *@author cj
     *@date 2022/4/21 9:34
     * @param topic
     * @return Integer
     */
    Integer insertTopic (Topic topic);

    /**
     *获取星球下所有帖子功能
     *@author cj
     *@date 2022/4/21 12:26
     * @param planetCode
     * @return List<Topic>
     */
    List<TopicVO> getAllTopic(Long planetCode);
}
