package com.major.knowledgePlanet.mapper;


import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.entity.ResourceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface ResourceMapper {

    /**
     *上传资源
     *@author Covenant
     *@date 2022/4/21 9:18
     * @param resource
     * @return Integer
     */
    Integer addResource (Resource resource);

    /**
     *更新点赞数
     *@author Covenant
     *@date 2022/4/21 9:18
     * @param resource
     * @return Integer
     */
    Integer upDatePraise(Resource resource);

    /**
     *根据资源id获取资源
     *@author Covenant
     *@date 2022/4/21 9:18
     * @param resourceId
     * @return Resource
     */
    Resource getResourceById(Long resourceId);

    /**
     *根据星球编号获取资源
     *@author Covenant
     *@date 2022/4/21 9:19
     * @param planetCode
     * @param userId
     * @return List<ResourceVO>
     */
    List<ResourceVO> getResourceByPCode(Long planetCode, Long userId);

    /**
     *点赞资源
     *@author Covenant
     *@date 2022/4/21 9:19
     * @param resourceId
     * @param delta
     * @return Integer
     */
    Integer addResourceLikeCount(Long resourceId,Integer delta);

    /**
     *收藏资源
     *@author Covenant
     *@date 2022/4/21 9:20
     * @param resourceId
     * @param delta
     * @return Integer
     */
    Integer addResourceCollectCount(Long resourceId,Integer delta);

    /**
     *修改用户资源关系表
     *@author Covenant
     *@date 2022/4/21 9:20
     * @param resourceId
     * @param userId
     * @param liked
     * @param collected
     * @return Integer
     */
    Integer changeUserResourceUserRel(Long resourceId,Long userId,Boolean liked,Boolean collected);

    /**
    * 查询用户收藏的所有资源
    * @param userId 用户id
    * @return : java.util.List<com.major.knowledgePlanet.entity.ResourceVO>
    * @author Covenant
    * @date 2022-04-16 19:57
    */
    List<ResourceVO> getCollectResourceByUserId(Long userId);

    /**
    * 在资源用户关系中查找某项
    * @param userId 1
    * @param resourceId 2
    * @return : java.lang.Integer 查找到的条数，只有1和0两种情况，表示存在与否
    * @author Covenant
    * @date 2022-04-16 20:59
    */
    Integer getUserResourceRelCount(Long userId,Long resourceId);

    /**
    * 在资源用户关系中添加一项
    * @param userId 1
    * @param resourceId 2
    * @param isLike 3
    * @param isCollect 4
    * @author Covenant
    * @date 2022-04-16 21:03
    */
    void addUserResourceRel(Long userId,Long resourceId,Boolean isLike,Boolean isCollect);

    /**
    * 添加资源标签
    * @param tagList 1
    * @param resourceId 2
    * @author Covenant
    * @date 2022-04-19 20:42
    */
    void addTag(ArrayList<String> tagList,Long resourceId);

}
