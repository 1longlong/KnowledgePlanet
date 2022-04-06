package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User getUserById(String u_id);

    User getUserByEmail(String email);

    Integer addUser(User user);
}
