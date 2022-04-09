package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginLogMapper {
    Integer addLoginLog(LoginLog loginLog);

    List<LoginLog> getLoginLogById(Long u_id);
}
