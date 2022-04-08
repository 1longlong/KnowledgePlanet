package com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏夹实体类
 *
 * @author cj
 * @date 2022/4/8 20:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Long u_id;
    private Long r_id;
    private String r_name;
    private String coverage;
}
