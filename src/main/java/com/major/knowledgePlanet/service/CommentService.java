package com.major.knowledgePlanet.service;

import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.entity.Comment;
import com.major.knowledgePlanet.entity.Reply;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {

    /**
    * 添加评论或回复
    * @param userId 1
    * @param topicId 2
    * @param parentId 3
    * @param content 4
    * @author Covenant
    * @date 2022-04-27 9:05
    */
    void addComment(Long userId,Long topicId,Long parentId,String content) throws Exception;



    /**
    * 获取话题下的一级评论和回复数量
    * @param topicId 1
    * @return : com.alibaba.fastjson.JSONObject
    * @author Covenant
    * @date 2022-04-27 9:57
    */
    JSONObject getFirstCommentWithReplyCount(Long topicId);


    /**
    * 获取评论的所有回复
    * @param parentId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Reply>
    * @author Covenant
    * @date 2022-04-27 10:58
    */
    List<Reply> getAllReply(Long parentId);
}
