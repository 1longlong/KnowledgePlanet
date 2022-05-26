package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.constValue.RedisKey;
import com.major.knowledgePlanet.entity.*;
import com.major.knowledgePlanet.mapper.MessageMapper;
import com.major.knowledgePlanet.mapper.PlanetMapper;
import com.major.knowledgePlanet.mapper.ResourceMapper;
import com.major.knowledgePlanet.service.ResourceService;
import com.major.knowledgePlanet.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    private PlanetMapper planetMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private MessageMapper messageMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer uploadResource (UploadResourceVO uploadResourceVO, Long userId){
        Resource resource=new Resource(uploadResourceVO);
        Date currentTime = new Date();
        resource.setUserId(userId);
        resource.setUploadTime(currentTime);
        resource.setStatus(0);
        Integer result= resourceMapper.addResource(resource);
        resourceMapper.addTag(uploadResourceVO.getTagList(),resource.getResourceId());
        Long planetOwnerId = planetMapper.getPlanetOwnerId(uploadResourceVO.getPlanetCode());
        Planet planet = planetMapper.getPlanetByPlanetCode(uploadResourceVO.getPlanetCode());
        Message message = new Message();
        message.setFrom(userId);
        message.setTo(planetOwnerId);
        message.setTime(new Date());
        message.setContent("您有一条来自星球<"+planet.getPlanetName()+">的推荐资源申请，请及时审核");
        message.setStatus(0);
        messageMapper.addMessage(message);
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
    public List<ResourceVO> getResourceByPCode(Long planetCode) {
        return resourceMapper.adminGetResourceByPCode(planetCode);
    }

    @Override
    public List<ResourceVO> getCollectResourceByUserId(Long userId) {
        return resourceMapper.getCollectResourceByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void checkResource(Long fromId, Long toId,Long resourceId, String resourceName, String checkInfo, Integer checkResult) throws Exception {
        Message message = new Message();
        message.setFrom(fromId);
        message.setTo(toId);
        String result=checkResult==1?"通过":"拒绝,原因如下："+checkInfo;
        message.setContent("您申请上传的资源结果为"+result);
        message.setTime(new Date());
        message.setStatus(0);
        try{
        messageMapper.addMessage(message);
        resourceMapper.changeResourceStatus(resourceId,checkResult,checkInfo,null);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("sql执行错误");
        }
    }

    @Override
    public Integer changeRecommendStatus(Long resourceId,Integer isRecommended){
        return resourceMapper.changeRecommendStatus(resourceId,isRecommended);
    }

    @Override
    public void freezeOrThawResource(Long resourceId,Integer type) {
        resourceMapper.freezeOrThawResource(resourceId,type);
    }

    ;


}

