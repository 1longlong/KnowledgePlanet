package com.major.knowledgePlanet.constValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* 用户账号状态码
*
* @author Covenant
* @date 2022-04-11 20:46
*/
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    /**
    * 可用
    */
    ENABLE(1),
    /**
    * 冻结
    */
    DISABLE(2);

    /**
    * 表示用户账号可用状态的标志
    */
    private final Integer status;
}
