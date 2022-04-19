package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.constValue.RedisKey;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.entity.ResourceVO;
import com.major.knowledgePlanet.entity.UploadResourceVO;
import com.major.knowledgePlanet.mapper.ResourceMapper;
import com.major.knowledgePlanet.service.ResourceService;
import com.major.knowledgePlanet.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cj
 * @date 2022/4/8 22:00
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ResourceMapper resourceMapper;


    @Transactional
    @Override
    public Integer uploadResource (UploadResourceVO uploadResourceVO, Long userId){
        Resource resource=new Resource(uploadResourceVO);
        Date currentTime = new Date();
        resource.setUserId(userId);
        resource.setUploadTime(currentTime);
        resource.setStatus(0);
        Integer result= resourceMapper.addResource(resource);
        resourceMapper.addTag(uploadResourceVO.getTagList(),resource.getResourceId());
        return result;
    }


    @Override
    public Integer  upDatePraise(Resource resource){return resourceMapper.upDatePraise(resource);}

    @Override
    public Resource getResourceById(Long resourceId) {
        return resourceMapper.getResourceById(resourceId);
    }


    @Override
    public List<ResourceVO> getResourceByPCode(Long planetCode, Long userId) {
        return resourceMapper.getResourceByPCode(planetCode,userId);
    }

    @Override
    public List<ResourceVO> getCollectResourceByUserId(Long userId) {
        return resourceMapper.getCollectResourceByUserId(userId);
    }


}

