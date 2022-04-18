package com.major.knowledgePlanet.mapper;


import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.entity.ResourceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResourceMapper {

    Integer uploadResource (Resource resource);

    Integer upDatePraise(Resource resource);

    Resource getResourceById(Long resourceId);

    List<ResourceVO> getResourceByPCode(Long planetCode, Long userId);

    Integer addResourceLikeCount(Long resourceId,Integer delta);

    Integer addResourceCollectCount(Long resourceId,Integer delta);

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

}
