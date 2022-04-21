package com.major.knowledgePlanet.service.impl;


import com.major.knowledgePlanet.entity.Comment;
import com.major.knowledgePlanet.mapper.CommentMapper;
import com.major.knowledgePlanet.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *评论类
 *@author cj
 *@date 2022/4/21 12:52
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentOfTopic(Long topicId){return  commentMapper.getCommentOfTopic(topicId);}
}
