package
        com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.VO.ActivityVO;
import com.major.knowledgePlanet.entity.Activity;
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
    * 获取星球中所有通过审核的活动以及和当前用户的关系
    * @param userId 1 当前用户
    * @param planetCode 2 星球
    * @return : java.util.List<com.major.knowledgePlanet.VO.ActivityVO>
    * @author Covenant
    * @date 2022-05-02 11:27
    */
    List<ActivityVO> getActivity(Long userId,Long planetCode);

}
