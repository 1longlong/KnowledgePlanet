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
public class Favorites {
    private Long userId;
    private Long resourceId;
    private Boolean isLiked;
    private Boolean isCollected;
}