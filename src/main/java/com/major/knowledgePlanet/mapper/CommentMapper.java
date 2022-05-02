package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Comment;
import com.major.knowledgePlanet.entity.CommentDTO;
import com.major.knowledgePlanet.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.RepeatableContainers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    /**
    * 添加评论或回复
    * @param comment 1
    * @author Covenant
    * @date 2022-04-27 9:01
    */
    void addComment(Comment comment);

    /**
    * 获取话题下的一级评论
    * @param topicId 话题id
    * @return : java.util.List<com.major.knowledgePlanet.entity.CommentDTO>
    * @author Covenant
    * @date 2022-04-27 9:55
    */
    List<CommentDTO> getFirstComment(Long topicId,Long userId);

    /**
    * 获取话题下所有评论数量
    * @param topicId 1
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2022-04-27 10:03
    */
    Integer getReplyCount(Long topicId);

    /**
    * 获取一级评论的所有回复
    * @param commentId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Reply>
    * @author Covenant
    * @date 2022-04-27 10:26
    */
    List<Reply> getAllReply(Long commentId,Long userId);

    /**
    * 更改评论点赞表,没有就添加一条记录
    * @param commentId 1
    * @param userId 2
    * @param liked 是否点赞
    * @author Covenant
    * @date 2022-05-01 14:01
    */
    void changeCommentUserRel(Long commentId,Long userId,Boolean liked);

    /**
    * 修改点赞数
    * @param commentId 评论id
    * @param count 正数表示点赞，负数表示取消点赞
    * @author Covenant
    * @date 2022-05-01 14:20
    */
    void addPraiseCount(Long commentId,Integer count);

}
