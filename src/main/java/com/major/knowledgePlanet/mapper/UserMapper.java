package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /**
     *根据邮件获取用户信息
     *@author Covenant
     *@date 2022/4/20 16:45
     * @param email
     * @return User
     */
    User getUserByEmail(String email);

    /**
     *用户注册
     *@author Covenant
     *@date 2022/4/20 16:46
     * @param user
     * @return Integer
     */
    Integer addUser(User user);

    /**
     *根据id查找用户信息
     *@author Covenant
     *@date 2022/4/20 16:46
     * @param userId
     * @return User
     */
    User getUserById(Long userId);

    /**
     *修改用户密码
     *@author Covenant
     *@date 2022/4/20 16:46
     * @param userId
     * @param newPassword
     * @return Integer
     */
    Integer updatePasswordByUserId(Long userId,String newPassword);

    /**
     *根据邮件修改用户密码
     *@author Covenant
     *@date 2022/4/20 16:47
     * @param email
     * @param newPassword
     * @return Integer
     */
    Integer updatePasswordByEmail(String email,String newPassword);


    /**
     *根据用户id更改用户名
     *@author cj
     *@date 2022/5/7 11:18
     * @param userId
     * @param newName
     * @return Integer
     */
    Integer updateNameByUserId(Long userId ,String newName);
}
