package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Planet;

import java.util.List;

public interface PlanetService {
    Integer addPlanet(Planet planet);

    /**
     * 创建一个星球
     * @param userId 1
     * @param planetName 2
     * @param planetDescription 3
     * @param planetAvatar 4
     * @return : java.lang.String 返回星球唯一标识序号,失败则返回null
     * @author Covenant
     * @date 2022-04-12 21:09
     */
    Long createPlanet(Long userId,String planetName,String planetDescription,String planetAvatar);

    /**
    * 搜索星球
    * @param info 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Planet>
    * @author Covenant
    * @date 2022-04-13 15:58
    */
    List<Planet> searchPlanet(String info);

    /**
    * 获取推荐星球
    * @param userId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Planet>
    * @author Covenant
    * @date 2022-04-13 15:58
    */
    List<Planet> getRecommendPlanet(Long userId);

    List<Planet> getHotestPlanet();


}
