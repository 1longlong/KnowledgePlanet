package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.VO.ActivityVO;
import com.major.knowledgePlanet.entity.Activity;

import java.util.List;

public interface ActivityService {

    void addActivity(Activity activity,Long userId);


    List<ActivityVO> getActivity(Long userId, Long planetCode);

}
