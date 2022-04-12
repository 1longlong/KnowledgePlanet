package com.major.knowledgePlanet;

import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.mapper.PlanetMapper;
import com.major.knowledgePlanet.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private PlanetMapper planetMapper;
    @Test
    void contextLoads() {
        Planet planet=new Planet();
        planet.setCreateTime(new Date());
        planet.setPlanetName("dfg");
        planet.setPlanetDescription("dgfg");
        planet.setMaxNumber(500);
        planet.setPlanetAvatar("sdf");
        planet.setHot(0);
        planetMapper.addPlanet(planet);
        System.out.println(planet.getPlanetCode());
    }

}
