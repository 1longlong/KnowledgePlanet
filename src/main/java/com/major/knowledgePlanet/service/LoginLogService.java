package com.major.knowledgePlanet.service;

import cn.hutool.core.date.DateTime;
import com.major.knowledgePlanet.entity.LoginLog;

import java.util.Date;
import java.util.List;

public interface LoginLogService {
    Integer addLoginLog(LoginLog loginLog);


    List<LoginLog> getLoginLogById(Long userId);


    List<String> getActiveCalender(Long userId);
}
