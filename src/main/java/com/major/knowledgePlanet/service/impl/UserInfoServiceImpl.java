package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.mapper.UserMapper;
import com.major.knowledgePlanet.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO:此处写UserInfoServiceImpl类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/5 23:17
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String u_id) {
        return userMapper.getUserById(u_id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }
}
