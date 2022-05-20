package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.VO.ActivityVO;
import com.major.knowledgePlanet.entity.Activity;
import com.major.knowledgePlanet.entity.User;

import java.util.List;

public interface ActivityService {

    void addActivity(Activity activity,Long userId);


    List<ActivityVO> getActivity(Long userId, Long planetCode);

    void cancelActivity(Long activityId);


    void checkActivity(Long userId,Long activityId,String checkInfo,Integer checkResult);

    void joinOrQuitActivity(Long activity,Long userId,Integer type) throws Exception;

    Integer getActivityNum(Long userId,Integer role);

    List<User>getActivityMember(Long activityId);

}
