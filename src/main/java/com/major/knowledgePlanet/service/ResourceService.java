package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.entity.ResourceVO;
import com.major.knowledgePlanet.entity.UploadResourceVO;

import java.util.List;

public interface ResourceService {
    /**
     *上传资源
     *@author Covenant
     *@date 2022/4/21 9:35
     * @param uploadResourceVO
     * @param userId
     * @return Integer
     */
    Integer uploadResource (UploadResourceVO uploadResourceVO, Long userId);

    /**
     *更新点赞数
     *@author Covenant
     *@date 2022/4/21 9:36
     * @param resource
     * @return Integer
     */
    Integer upDatePraise(Resource resource);

    /**
     *根据id获取资源
     *@author Covenant
     *@date 2022/4/21 9:36
     * @param resourceId
     * @return Resource
     */
    Resource getResourceById(Long resourceId);

    /**
     *根据星球id获取资源
     *@author Covenant
     *@date 2022/4/21 9:36
     * @param planetCode
     * @param userId
     * @return List<ResourceVO>
     */
    List<ResourceVO> getResourceByPCode(Long planetCode,Long userId);

    /**
    * 查找用户所有收藏的资源
    * @param userId 用户id
    * @return : java.util.List<com.major.knowledgePlanet.entity.Resource>
    * @author Covenant
    * @date 2022-04-16 19:47
    */
    List<ResourceVO> getCollectResourceByUserId (Long userId);


    void checkResource(Long fromId,Long toId,Long resourceId, String resourceName, String checkInfo,Integer checkResult) throws Exception;

    /**
     *
     *@author cj
     *@date 2022/4/26 21:01
     * @param resourceId
     * @param isRecommended
     * @return Integer
     */

    Integer changeRecommendStatus(Long resourceId,Integer isRecommended);
}
