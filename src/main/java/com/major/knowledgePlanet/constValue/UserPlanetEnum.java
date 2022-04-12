package com.major.knowledgePlanet.constValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPlanetEnum {

    /**
    * 创建者
    */
    CREATER(1),
    /**
    * 普通成员
    */
    COMMON_MEMBER(2);
    /**
    * 用户在星球中的角色
    */
    private Integer role;
}
