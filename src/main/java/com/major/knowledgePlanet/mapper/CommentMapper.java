package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    /**
     *获取topic下的评论
     *@author cj
     *@date 2022/4/21 12:56
     * @param topicId
     * @return List<Comment>
     */
    List<Comment> getCommentOfTopic(Long topicId);
}
