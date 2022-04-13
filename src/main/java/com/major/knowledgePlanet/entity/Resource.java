package com.major.knowledgePlanet.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 资源实体类
 *
 * @author cj
 * @date 2022/4/8 20:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    //TODO 数据库加details
    private Long resourceId;
    private Long userId;
    private Long planetCode;
    private String resourceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;
    private String link;
    private String coverage;
    private String resourceDescription;
    private Integer status;
    private String checkInfo;
    private Boolean isRecommended;
    private Integer likeCount;
    private Integer collectCount;
    private String details;

}
