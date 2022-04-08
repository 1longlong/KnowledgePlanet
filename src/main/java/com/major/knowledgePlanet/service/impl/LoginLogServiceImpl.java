package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.mapper.LoginLogMapper;
import com.major.knowledgePlanet.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO:此处写LoginLogServiceImpl类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/7 12:23
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public Integer addLoginLog(LoginLog loginLog) {
        return loginLogMapper.addLoginLog(loginLog);
    }

    @Override
    public List<LoginLog> getLoginLogById(Long u_id){return loginLogMapper.getLoginLogById(u_id);}
}
