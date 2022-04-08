package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;

public interface ResourceService {
    Integer uploadResource (Resource resource);

    Integer upDatePraise(Resource resource);

    Resource getResourceById(Long r_id);


}
