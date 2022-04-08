package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.mapper.ResourceMapper;
import com.major.knowledgePlanet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cj
 * @date 2022/4/8 22:00
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;


    @Override
    public Integer uploadResource (Resource resource){return resourceMapper.uploadResource(resource);}


    @Override
    public Integer  upDatePraise(Resource resource){return resourceMapper.upDatePraise(resource);}


    @Override
    public Resource getResourceById(Long r_id){return resourceMapper.getResourceById(r_id);}
}

