package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.mapper.UserMapper;
import com.major.knowledgePlanet.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public Integer updatePassword(Long userId, String newPassword) {
        return userMapper.updatePasswordByUserId(userId,newPassword);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
         userMapper.updatePasswordByEmail(email,newPassword);
    }

    @Override
    public Integer updateNameByUserId(Long userId ,String newName){
        return userMapper.updateNameByUserId(userId,newName);}

    @Override
    public Integer updateAvatarByUserId(Long userId ,String newAvatar){
        return userMapper.updateAvatarByUserId(userId,newAvatar);}

    @Override
    public Integer countName(String userName){
        return userMapper.countName(userName);
    }

    @Override
    public List<User> searchUser(String info) {
        return userMapper.searchUser(info);
    }

    @Override
    public void changeUserStatus(Long userId, Integer status) {
        userMapper.changeUserStatus(userId,status);
    }

    @Override
    public String getAdminPassword(String adminId) {
        return userMapper.getAdminPassword(adminId);
    }

}
