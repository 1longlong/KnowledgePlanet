package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.VO.ActivityVO;
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
import java.util.List;

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
        activity.setCurNumber(1);
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

    @Override
    public List<ActivityVO> getActivity(Long userId, Long planetCode) {
        return activityMapper.getActivity(userId,planetCode);
    }

    @Override
    public void cancelActivity(Long activityId) {
        activityMapper.setStatus(activityId,3);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkActivity(Long userId, Long activityId, String checkInfo, Integer checkResult) {
        ActivityVO activity = activityMapper.getActivityById(activityId,userId);
        String result="";
        if(checkResult==1){
            activityMapper.setStatus(activityId,1);
            result="通过";
        }else if(checkResult==2){
            activityMapper.setStatus(activityId,2);
            result="拒绝,原因:"+checkInfo;
        }
        String content="您提交的活动 "+activity.getActivity().getTitle()+" 审核结果为:"+result;
        Message message = new Message();
        message.setContent(content);
        message.setFrom(userId);
        message.setTo(activity.getOrganizerId());
        message.setTime(new Date());
        message.setStatus(0);
        messageMapper.addMessage(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinOrQuitActivity(Long activityId, Long userId,Integer type) throws Exception {
        if(type==1){
            activityMapper.addRel(activityId,userId,0);
            activityMapper.changeCurNumber(activityId,1);
        }else if(type==0){
            if(activityMapper.getRole(activityId,userId)==1){
                activityMapper.deleteActivity(activityId);
                activityMapper.deleteUserActivityRelById(activityId);
            }
            activityMapper.deleteUserActivityRel(activityId,userId);
            activityMapper.changeCurNumber(activityId,-1);
        }else{
            throw new Exception("参数错误");
        }
    }

    @Override
    public Integer getActivityNum(Long userId, Integer role) {
        return activityMapper.getActivityNum(userId,role);
    }

}
