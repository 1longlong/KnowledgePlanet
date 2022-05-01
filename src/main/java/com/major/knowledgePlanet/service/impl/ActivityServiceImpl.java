package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Activity;
import com.major.knowledgePlanet.entity.Message;
import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.mapper.ActivityMapper;
import com.major.knowledgePlanet.mapper.MessageMapper;
import com.major.knowledgePlanet.mapper.PlanetMapper;
import com.major.knowledgePlanet.mapper.UserMapper;
import com.major.knowledgePlanet.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * TODO:此处写ActivityServiceImpl类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/1 21:39
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlanetMapper planetMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addActivity(Activity activity,Long userId) {
        activity.setStatus(0);
        activity.setCreateTime(new Date());
        activity.setCurNumber(0);
        activityMapper.addActivity(activity);
        activityMapper.addRel(activity.getActivityId(),userId,1);
        //获取当前用户
        User user = userMapper.getUserById(userId);
        //获取星主id
        Long planetOwnerId = planetMapper.getPlanetOwnerId(activity.getPlanetCode());
        //获取星球
        Planet planet = planetMapper.getPlanetByPlanetCode(activity.getPlanetCode());
        Message message = new Message();
        message.setFrom(userId);
        message.setTo(planetOwnerId);
        message.setStatus(0);
        String content=user.getUserName()+"在星球"+planet.getPlanetName()+"发布了一条活动拼团申请，请及时审核";
        message.setContent(content);
        message.setTime(new Date());
        messageMapper.addMessage(message);
    }
}
