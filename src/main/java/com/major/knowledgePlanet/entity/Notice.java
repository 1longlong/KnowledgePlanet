package com.major.knowledgePlanet.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 收藏夹实体类
 *
 * @author cj
 * @date 2022/4/13 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Long noticeId;
    private String adminId;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer status;
    private String content;
}
