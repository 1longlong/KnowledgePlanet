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
public class Planet {
    private Long pCode;
    private String pName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer maxNumber;
    private String pDescription;
    private String pAvatar;
    private Integer hot;

}
