package
        com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}
