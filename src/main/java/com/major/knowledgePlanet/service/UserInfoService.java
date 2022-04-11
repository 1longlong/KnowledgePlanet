package
        com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.User;
import org.springframework.stereotype.Service;

/**
 * TODO:此处写UserInfo类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/5 23:15
 */
public interface UserInfoService {

    User getUserByEmail(String email);

    Integer addUser(User user);

    User getUserById(Long u_id);

    Integer updatePassword(Long u_id,String newPassword);
}
