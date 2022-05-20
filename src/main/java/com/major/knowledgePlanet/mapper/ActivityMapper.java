package
        com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.VO.ActivityVO;
import com.major.knowledgePlanet.entity.Activity;
import com.major.knowledgePlanet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO:此处写ActivityMapper类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/1 21:23
 */
@Mapper
@Repository
public interface ActivityMapper {

    /**
    * 添加活动
    * @param activity 1
    * @author Covenant
    * @date 2022-05-01 21:28
    */
    void addActivity(Activity activity);

    /**
    * 向用户活动关系表中添加一项
    * @param activityId 1
    * @param userId 2
    * @param role 3
    * @author Covenant
    * @date 2022-05-01 21:49
    */
    void addRel(Long activityId,Long userId,Integer role);

    /**
    * 获取星球中所有活动以及和当前用户的关系
    * @param userId 1 当前用户
    * @param planetCode 2 星球
    * @return : java.util.List<com.major.knowledgePlanet.VO.ActivityVO>
    * @author Covenant
    * @date 2022-05-02 11:27
    */
    List<ActivityVO> getActivity(Long userId,Long planetCode);

    /**
    * 取消活动
    * @param activityId 1
    * @author Covenant
    * @date 2022-05-02 20:45
    */
    void setStatus(Long activityId,Integer status);

    /**
    * 根据id查活动具体信息
    * @param activityId 1
    * @return : com.major.knowledgePlanet.entity.Activity
    * @author Covenant
    * @date 2022-05-02 21:28
    */
    ActivityVO getActivityById(Long activityId,Long userId);

    /**
    * 修改活动当前人数
    * @param activityId 1
    * @param delta 正数表示增加，负数表示减少
    * @author Covenant
    * @date 2022-05-07 17:45
    */
    void changeCurNumber(Long activityId,Integer delta);

    /**
    * 删除用户关系
    * @param activityId 1
    * @param userId 2
    * @author Covenant
    * @date 2022-05-07 17:47
    */
    void deleteUserActivityRel(Long activityId,Long userId);
    /**
    * 获取用户在活动中的角色
    * @param activityId 1
    * @param userId 2
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2022-05-07 18:14
    */
    Integer getRole(Long activityId,Long userId);

    /**
    * 取消活动
    * @param activityId 1
    * @author Covenant
    * @date 2022-05-07 18:15
    */
    void deleteActivity(Long activityId);

    /**
    * 根据活动id删除所有活动和用户参与关系
    * @param activityId 1
    * @author Covenant
    * @date 2022-05-08 15:14
    */
    void deleteUserActivityRelById(Long activityId);


    /**
    * 统计创建和参与的活动个数
    * @param userId 1
    * @param role 0表示参与的,1表示创建的
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2022-05-07 18:24
    */
    Integer getActivityNum(Long userId,Integer role);

    /**
    * 获取活动创建人以外的所有参与者
    * @param activityId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.User>
    * @author Covenant
    * @date 2022-05-18 15:47
    */
    List<User>getActivityMember(Long activityId);

}
