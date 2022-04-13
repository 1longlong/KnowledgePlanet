package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.entity.UserPlanetDTO;
import com.major.knowledgePlanet.entity.UserPlanetRel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanetMapper {
    Integer addPlanet(Planet planet);

    Integer addUserPlanetRel(UserPlanetRel userPlanetRel);

    /**
    * 根据关键词查找星球
    * @param info 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Planet>
    * @author Covenant
    * @date 2022-04-13 14:24
    */
    List<Planet> searchPlanet(String info);

    /**
    * 查找用户创建或加入的星球
    * @param userId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.UserPlanetDTO>
    * @author Covenant
    * @date 2022-04-13 16:20
    */
    List<UserPlanetDTO> getAllPlanetByUserId(Long userId);

    List<Planet> getHotestPlanet();
}
