package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.entity.UserPlanetRel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanetMapper {
    Integer addPlanet(Planet planet);

    Integer addUserPlanetRel(UserPlanetRel userPlanetRel);

    List<Planet> getHotestPlanet();
}
