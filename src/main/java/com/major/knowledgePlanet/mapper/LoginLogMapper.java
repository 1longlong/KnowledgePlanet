package com.major.knowledgePlanet.mapper;

import cn.hutool.core.date.DateTime;
import com.major.knowledgePlanet.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface LoginLogMapper {
    Integer addLoginLog(LoginLog loginLog);

    List<LoginLog> getLoginLogById(Long userId);

    List<String> getActiveCalender(Long userId);
}
