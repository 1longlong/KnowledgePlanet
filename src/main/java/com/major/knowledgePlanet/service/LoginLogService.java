package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.LoginLog;

import java.util.List;

public interface LoginLogService {
    Integer addLoginLog(LoginLog loginLog);

    List<LoginLog> getLoginLogById(Long u_id);
}
