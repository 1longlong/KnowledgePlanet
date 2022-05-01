package com.major.knowledgePlanet.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.entity.Comment;
import com.major.knowledgePlanet.entity.CommentDTO;
import com.major.knowledgePlanet.entity.Reply;
import com.major.knowledgePlanet.mapper.CommentMapper;
import com.major.knowledgePlanet.mapper.TopicMapper;
import com.major.knowledgePlanet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 评论类
 *
 * @author cj
 * @date 2022/4/21 12:52
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TopicMapper topicMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addComment(Long userId, Long topicId, Long parentId, String content) throws Exception {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setParentId(parentId);
        comment.setContent(content);
        comment.setTopicId(topicId);
        comment.setTime(new Date());
        comment.setPraiseCount(0);
        try {
            topicMapper.changeCommentCount(topicId, 1);
            commentMapper.addComment(comment);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public JSONObject getFirstCommentWithReplyCount(Long topicId, Long userId) {
        JSONObject obj = new JSONObject();
        List<CommentDTO> firstComment = commentMapper.getFirstComment(topicId, userId);
        Integer replyCount = commentMapper.getReplyCount(topicId);
        obj.put("commentList", firstComment);
        obj.put("replyCount", replyCount);
        return obj;
    }

    @Override
    public List<Reply> getAllReply(Long parentId) {
        return commentMapper.getAllReply(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void praise(Long commentId, Long userId) throws Exception {
        try {
            commentMapper.changeCommentUserRel(commentId, userId, true);
            commentMapper.addPraiseCount(commentId, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unPraise(Long commentId, Long userId)throws Exception {
        try {
            commentMapper.changeCommentUserRel(commentId,userId,false);
            commentMapper.addPraiseCount(commentId,-1);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }
}
