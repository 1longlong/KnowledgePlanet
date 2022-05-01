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
    * @param firstCommentId 5 一级评论id,没有则为null
    * @author Covenant
    * @date 2022-04-27 9:05
    */
    void addComment(Long userId,Long topicId,Long parentId,String content,Long firstCommentId) throws Exception;



    /**
    * 获取话题下的一级评论和回复数量
    * @param topicId
    * @Param userId 当前登录的用户id
    * @return : com.alibaba.fastjson.JSONObject
    * @author Covenant
    * @date 2022-04-27 9:57
    */
    JSONObject getFirstCommentWithReplyCount(Long topicId,Long userId);


    /**
    * 获取一级评论的所有回复
    * @param commentId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Reply>
    * @author Covenant
    * @date 2022-04-27 10:58
    */
    List<Reply> getAllReply(Long commentId);


    /**
    * 点赞评论
    * @param commentId 1
    * @param userId 2
    * @author Covenant
    * @date 2022-05-01 14:24
    */
    void praise(Long commentId,Long userId) throws Exception;

    /**
    * 取消点赞评论
    * @param commentId 1
    * @param userId 2
    * @author Covenant
    * @date 2022-05-01 14:27
    */
    void unPraise(Long commentId,Long userId)throws Exception;
}
