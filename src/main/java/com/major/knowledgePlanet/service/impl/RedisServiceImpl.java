package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.constValue.RedisKey;
import com.major.knowledgePlanet.mapper.ResourceMapper;
import com.major.knowledgePlanet.service.RedisService;
import com.major.knowledgePlanet.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * redis服务
 *
 * @author 孟繁霖
 * @date 2022/4/15 21:56
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void persistResourceLikeCount() {
        Map<Object, Object> resourceLikeMap = redisUtil.hmget(RedisKey.RESOURCE_LIKE_COUNT);
        for(Map.Entry<Object,Object> entry:resourceLikeMap.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
            resourceMapper.addResourceLikeCount(Long.parseLong((String)entry.getKey()),(Integer)entry.getValue());
            redisUtil.hdel(RedisKey.RESOURCE_LIKE_COUNT,entry.getKey());
        }
    }

    @Override
    public void persistResourceCollectCount() {
        Map<Object, Object> resourceCollectMap = redisUtil.hmget(RedisKey.RESOURCE_COLLECT_COUNT);
        for(Map.Entry<Object,Object> entry:resourceCollectMap.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
            resourceMapper.addResourceCollectCount(Long.parseLong((String)entry.getKey()),(Integer)entry.getValue());
            redisUtil.hdel(RedisKey.RESOURCE_COLLECT_COUNT,entry.getKey());
        }
    }

    @Override
    public void persistResourceUserIsCollect() {
        Map<Object, Object> map = redisUtil.hmget(RedisKey.RESOURCE_USER_ISCOLLECT);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            String hashKey= ((String) entry.getKey());
            Long resourceId=Long.valueOf(hashKey.split(":")[0]);
            Long userId=Long.valueOf(hashKey.split(":")[1]);
            if(resourceMapper.getUserResourceRelCount(userId,resourceId)>0){
                resourceMapper.changeUserResourceUserRel(resourceId,userId,null,entry.getValue().equals(1));
            }else{
                resourceMapper.addUserResourceRel(userId,resourceId,false,entry.getValue().equals(1));
            }
            redisUtil.hdel(RedisKey.RESOURCE_USER_ISCOLLECT,entry.getKey());
        }
    }

    @Override
    public void persistResourceUserIsLike() {
        Map<Object, Object> map = redisUtil.hmget(RedisKey.RESOURCE_USER_ISLIKE);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
             String hashKey= ((String) entry.getKey());
             Long resourceId=Long.valueOf(hashKey.split(":")[0]);
             Long userId=Long.valueOf(hashKey.split(":")[1]);
            if(resourceMapper.getUserResourceRelCount(userId,resourceId)>0){
                resourceMapper.changeUserResourceUserRel(resourceId,userId,entry.getValue().equals(1),null);
            }else{
                resourceMapper.addUserResourceRel(userId,resourceId,entry.getValue().equals(1),false);
            }
            redisUtil.hdel(RedisKey.RESOURCE_USER_ISLIKE,entry.getKey());
        }
    }
}
