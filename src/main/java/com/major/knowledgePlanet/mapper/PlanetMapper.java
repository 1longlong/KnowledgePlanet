package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.VO.SearchResultVO;
import com.major.knowledgePlanet.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    * @return : java.util.List<com.major.knowledgePlanet.VO.SearchResultVO>
    * @author Covenant
    * @date 2022-04-13 14:24
    */
    List<SearchResultVO> searchPlanet(String info,Long userId);

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
    * 根据用户推荐星球
    * @param userId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.RecommendPlanetVO>
    * @author Covenant
    * @date 2022-05-07 20:14
    */
    List<RecommendPlanetVO> getRecommendPlanet(Long userId);



    /**
     *根据星球id获取用户人数
     *@author cj
     *@date 2022/4/21 10:59
     * @param planetCode
     * @return Integer
     */
    Integer getMemNumOfPlanet(Long planetCode);

    /**
    * 获取星主的id
    * @param planetCode 1
    * @return : java.lang.Long
    * @author Covenant
    * @date 2022-04-20 17:43
    */
    Long getPlanetOwnerId(Long planetCode);


    /**
    * 通过星球id查找星球信息
    * @param planetCode 1
    * @return : com.major.knowledgePlanet.entity.Planet
    * @author Covenant
    * @date 2022-04-20 17:47
    */
    Planet getPlanetByPlanetCode(Long planetCode);

    /**
    * 获取创建/加入的星球
    * @param userId 1
    * @param role 2 1表示创建的星球，2表示加入的星球
    * @return : com.major.knowledgePlanet.entity.Planet
    * @author Covenant
    * @date 2022-04-21 20:50
    */
    List<UserPlanetVO> getPlanet(Long userId, Integer role);


    /**
    * 获取用户在星球中的积分
    * @param userId 1
    * @param planetCode 2
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2022-04-26 20:08
    */
    Integer getIntegral(Long userId, Long planetCode);

    /**
    * 获取用户积分排行
    * @param planetCode 1
    * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
    * @author Covenant
    * @date 2022-04-26 22:54
    */
    List<Map<String,Object>>getUserRank(Long planetCode);





    /**
     *获取星球内的用户
     *@author cj
     *@date 2022/4/26 21:27
     * @param planetId
     * @return List<User>
     */
    List<User> getMemberListOfPlanet(Long planetId);

    /**
     *删除成员
     *@author cj
     *@date 2022/4/26 22:02
     * @param userId
     * @return Integer
     */
    Integer deleteMember(Long userId ,Long planetCode);

    /**
    * 查询用户在星球中的角色
    * @param userId 1
    * @param planetCode 2
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2022-05-08 17:36
    */
    Integer getRole(Long userId,Long planetCode);


    List<Planet> getAllPlanet();


}
