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

  private Long rId;
  private Long uId;
  private Long pCode;
  private String uName;
  private String rName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date uploadTime;
  private String link;
  private String coverage;
  private String rDescription;
  private Boolean isChecked;
  private String remarks;
  private Boolean isRecommended;
  private Integer praiseCount;
  private Integer collectCount;


}
