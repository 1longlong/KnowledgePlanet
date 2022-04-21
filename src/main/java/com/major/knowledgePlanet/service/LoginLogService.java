package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.LoginLog;

import java.util.List;

public interface LoginLogService {
    /**
     *登录时添加登录日志
     *@author cj
     *@date 2022/4/20 16:31
     * @param loginLog
     * @return Integer
     */
    Integer addLoginLog(LoginLog loginLog);

    /**
     *根据用户id获取用户的登录日志
     *@author cj
     *@date 2022/4/20 16:31
     * @param u_id
     * @return List<LoginLog>
     */

    List<LoginLog> getLoginLogById(Long u_id);

    /**
     *获得用户的活跃日历
     *@author cj
     *@date 2022/4/20 16:32
     * @param userId
     * @return List<String>
     */
    List<String> getActiveCalender(Long userId);
}
