package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.entity.RecommendPlanetVO;
import com.major.knowledgePlanet.entity.UserPlanetDTO;
import com.major.knowledgePlanet.entity.UserPlanetRel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanetMapper {
    /**
     *创建星球
     *@author Covenant
     *@date 2022/4/21 9:17
     * @param planet
     * @return Integer
     */
    Integer addPlanet(Planet planet);

    /**
     *添加用户和星球关系
     *@author cj
     *@date 2022/4/21 9:17
     * @param userPlanetRel
     * @return Integer
     */
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

    /**
     *获取热度前五的星球
     *@author cj
     *@date 2022/4/21 10:58
     * @return List<Planet>
     */
    List<Planet> getHotestPlanet();


    /**
    * 获取推荐的星球
    * @return : java.util.List<com.major.knowledgePlanet.entity.RecommendPlanetVO>
    * @author Covenant
    * @date 2022-04-20 15:26
    */
    List<RecommendPlanetVO> getRecommendPlanet();


    /**
     *根据星球id获取用户人数
     *@author cj
     *@date 2022/4/21 10:59
     * @param planetCode
     * @return Integer
     */
    Integer getMemNumOfPlanet(Long planetCode);
}
