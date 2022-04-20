package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginLogMapper {
    /**
     *添加登录日志
     *@author Covenant
     *@date 2022/4/20 16:43
     * @param loginLog
     * @return Integer
     */
    Integer addLoginLog(LoginLog loginLog);

    /**
     *根据用户id获取登录日志
     *@author cj
     *@date 2022/4/20 16:44
     * @param userId
     * @return List<LoginLog>
     */
    List<LoginLog> getLoginLogById(Long userId);

    /**
     *获取活动日历
     *@author cj
     *@date 2022/4/20 16:44
     * @param userId
     * @return List<String>
     */
    List<String> getActiveCalender(Long userId);
}
