package com.major.knowledgePlanet;

import com.major.knowledgePlanet.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        Integer integer = userMapper.updatePassword(123L, "12345");
        System.out.println(integer);
    }

}
