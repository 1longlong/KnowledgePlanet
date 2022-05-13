package com.major.knowledgePlanet.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.VO.SearchResultVO;
import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.entity.RecommendPlanetVO;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.entity.UserPlanetVO;


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
    List<SearchResultVO> searchPlanet(String info,Long userId);

    /**
    * 获取推荐星球
    * @param userId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Planet>
    * @author Covenant
    * @date 2022-04-13 15:58
    */
    List<RecommendPlanetVO> getRecommendPlanet(Long userId);

    /**
     *返回星球热度排名前五的星球
     *@author cj
     *@date 2022/4/20 16:33
     * @return List<Planet>
     */
    List<Planet> getHotestPlanet();

    /**
     *根据星球id获取用户人数
     *@author cj
     *@date 2022/4/21 10:59
     * @param planetCode
     * @return Integer
     */
    Integer getMemNumOfPlanet(Long planetCode);

    /**
    * 获取用户创建或加入的星球及积分
    * @param userId 1
    * @param role 2 1表示创建的，2表示加入的
    * @return : com.major.knowledgePlanet.entity.UserPlanetVO
    * @author Covenant
    * @date 2022-04-21 21:06
    */
    List<UserPlanetVO> getPlanet(Long userId, Integer role);

    /**
    * 用户加入指定星球
    * @param userId 1
    * @param planetCode 2
    * @author Covenant
    * @date 2022-04-22 10:22
    */
    void joinPlanet(Long userId,Long planetCode) throws Exception;


    /**
    * 查找用户在星球中的积分和星球排行榜前50
    * @param userId 1
    * @param planetCode 2
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2022-04-26 20:04
    */
     JSONObject getLeaderboard(Long userId, Long planetCode);



    /**
     *获取星球内的用户列表
     *@author cj
     *@date 2022/4/26 21:30
     * @param planetId
     * @return List<User>
     */
    List<User> getMemberListOfPlanet(Long planetId);

    /**
     *删除成员
     *@author cj
     *@date 2022/4/26 22:03
     * @param userId
     * @return Integer
     */
    Integer deleteMember(Long userId ,Long planetCode);

    Integer getRole(Long userId,Long planetCode);
}
