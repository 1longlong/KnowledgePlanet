package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User getUserByEmail(String email);

    Integer addUser(User user);

    User getUserById(Long userId);

    Integer updatePasswordByUserId(Long userId,String newPassword);

    Integer updatePasswordByEmail(String email,String newPassword);
}
