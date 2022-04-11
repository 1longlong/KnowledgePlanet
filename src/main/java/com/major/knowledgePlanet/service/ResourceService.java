package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;

import java.util.List;

public interface ResourceService {
    Integer uploadResource (Resource resource);

    Integer upDatePraise(Resource resource);

    Resource getResourceById(Long r_id);

    List<Resource> getResourceByPCode(Long p_code);


}
