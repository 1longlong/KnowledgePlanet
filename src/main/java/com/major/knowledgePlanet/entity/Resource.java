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

<<<<<<< Updated upstream
  private Long r_id;
  private Long u_id;
  private Long p_code;
  private String u_name;
  private String r_name;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date upload_time;
  private String link;
  private String coverage;
  private String r_description;
  private Boolean is_checked;
  private String remarks;
  private Boolean is_recommended;
  private Integer praise_count;
  private Integer collect_count;
=======
    private Long r_id;
    private Long u_id;
    private Long p_code;
    private String u_name;
    private String r_name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date upload_time;
    private String link;
    private String coverage;
    private String r_description;
    private Boolean is_checked;
    private String remarks;
    private Boolean is_recommended;
    private Integer praise_count;
    private Integer collect_count;
>>>>>>> Stashed changes


}
