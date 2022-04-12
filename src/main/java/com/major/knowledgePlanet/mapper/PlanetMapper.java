package com.major.knowledgePlanet.mapper;


import com.major.knowledgePlanet.entity.Planet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanetMapper {

    List<Planet> getHotestPlanet();
}
