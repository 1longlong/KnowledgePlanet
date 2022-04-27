package com.major.knowledgePlanet.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *帖子评论类
 *@author cj
 *@date 2022/4/21 12:46
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long userId;
    private Long commentId;
    private Long topicId;
    private Long parentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer praiseCount;
    private String content;
}
