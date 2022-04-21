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

    /**
     *根据邮件获取用户
     *@author Covenant
     *@date 2022/4/20 16:34
     * @param email
     * @return User
     */
    User getUserByEmail(String email);

    /**
     *新增用户
     *@author Covenant
     *@date 2022/4/20 16:34
     * @param user
     * @return Integer
     */
    Integer addUser(User user);

    /**
     *根据用户id获取用户信息
     *@author Covenant
     *@date 2022/4/20 16:35
     * @param userId
     * @return User
     */
    User getUserById(Long userId);

    /**
     *更新用户的密码
     *@author Covenant
     *@date 2022/4/20 16:35
     * @param userId
     * @param newPassword
     * @return Integer
     */
    Integer updatePassword(Long userId,String newPassword);

    /**
     * 更新用户的密码
     *@author Covenant
     *@date 2022/4/20 16:36
     * @param email
     * @param newPassword
     */
    void updatePassword(String email,String newPassword);
}
