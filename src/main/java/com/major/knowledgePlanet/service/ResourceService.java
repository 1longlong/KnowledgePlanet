package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.entity.ResourceVO;
import com.major.knowledgePlanet.entity.UploadResourceVO;

import java.util.List;

public interface ResourceService {
    Integer uploadResource (UploadResourceVO uploadResourceVO, Long userId);

    Integer upDatePraise(Resource resource);

    Resource getResourceById(Long resourceId);

    List<ResourceVO> getResourceByPCode(Long planetCode,Long userId);

    /**
    * 查找用户所有收藏的资源
    * @param userId 用户id
    * @return : java.util.List<com.major.knowledgePlanet.entity.Resource>
    * @author Covenant
    * @date 2022-04-16 19:47
    */
    List<ResourceVO> getCollectResourceByUserId (Long userId);

}
