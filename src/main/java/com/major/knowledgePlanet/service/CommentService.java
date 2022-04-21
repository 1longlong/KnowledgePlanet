package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Comment;

import java.util.List;

public interface CommentService {
    /**
     *获取topic下所有评论
     *@author cj
     *@date 2022/4/21 12:58
     * @param topicId
     * @return List<Comment>
     */
    List<Comment> getCommentOfTopic(Long topicId);
}
