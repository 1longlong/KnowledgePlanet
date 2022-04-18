package com.major.knowledgePlanet.controller;


import com.major.knowledgePlanet.constValue.RedisKey;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.entity.ResourceVO;
import com.major.knowledgePlanet.result.Response;


import com.major.knowledgePlanet.service.RedisService;
import com.major.knowledgePlanet.service.ResourceService;
import com.major.knowledgePlanet.util.RedisUtil;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * 包含资源管理基本功能
 *
 * @author cj
 * @date 2022/4/8 20:19
 */
@Api(tags="资源模块",value="ResourceController")
@RestController
public class ResourceController {
    @Value("${saltValue}")
    private String saltValue;

    @Autowired
    private RedisUtil redisUtil;

    @javax.annotation.Resource(name="redisServiceImpl")
    private RedisService redisService;


    @Autowired
    private ResourceService resourceService;



    @PostMapping("resource/uploadResource")
    @ApiOperation(value="上传资源")
    public Response uploadResource(HttpServletRequest request, @RequestBody Resource resource){

        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        System.out.println("userId:" + userId);
        Date currentTime = new Date();
        resource.setUserId(userId);
        resource.setUploadTime(currentTime);
        resource.setStatus(0);
            int result = resourceService.uploadResource(resource);
            if (result != 0) {
                return Response.success().message("上传成功").data("r_id", resource.getResourceId()).data("upload_time", resource.getUploadTime());
            } else {
                return Response.serverError().message("上传失败").data("result", result);
            }
    }



    @PostMapping("resource/praise")
    @ApiOperation(value="资源点赞")
    @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)
    public Response praise(HttpServletRequest request, @RequestParam(value = "resourceId") Long resourceId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        System.out.println("userId:" + userId);

        if(resourceService.getResourceById(resourceId)==null) {
            return Response.clientError().message("资源不存在");
        }
        //向redis中写入一条记录或者加1
        if(!redisUtil.hHasKey(RedisKey.RESOURCE_LIKE_COUNT,resourceId.toString())){
            redisUtil.hset(RedisKey.RESOURCE_LIKE_COUNT,resourceId.toString(),1);
        }else{
            redisUtil.hincr(RedisKey.RESOURCE_LIKE_COUNT,resourceId.toString(),1);
        }
        //资源-用户-是否点赞设为是
        String hashKey= resourceId + ":" + userId;
        redisUtil.hset(RedisKey.RESOURCE_USER_ISLIKE,hashKey,1);
        return Response.success().message("点赞成功");
    }

    @PostMapping("resource/unPraise")
    @ApiOperation(value="取消点赞")
    @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)
    public Response unPraise(HttpServletRequest request, @RequestParam(value = "resourceId") Long resourceId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        //向redis写入一条记录或者减1
        if(!redisUtil.hHasKey(RedisKey.RESOURCE_LIKE_COUNT,resourceId.toString())){
            redisUtil.hset(RedisKey.RESOURCE_LIKE_COUNT,resourceId.toString(),-1);
        }else{
            redisUtil.hdecr(RedisKey.RESOURCE_LIKE_COUNT,resourceId.toString(),1);
        }
        //资源-用户-是否点赞设为否
        String hashKey= resourceId + ":" + userId;
        redisUtil.hset(RedisKey.RESOURCE_USER_ISLIKE,hashKey,0);
        return Response.success().message("取消点赞成功");

    }

    @PostMapping("resource/collect")
    @ApiOperation(value="添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    public Response collect(HttpServletRequest request,@RequestParam(value = "resourceId") Long resourceId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        System.out.println("userId:" + userId);
        if(resourceService.getResourceById(resourceId)==null) {
            return Response.clientError().message("资源不存在");
        }

        //向redis中写入一条记录或者加1
        if(!redisUtil.hHasKey(RedisKey.RESOURCE_COLLECT_COUNT,resourceId.toString())){
            redisUtil.hset(RedisKey.RESOURCE_COLLECT_COUNT,resourceId.toString(),1);
        }else{
            redisUtil.hincr(RedisKey.RESOURCE_COLLECT_COUNT,resourceId.toString(),1);
        }
        //资源-用户-是否收藏设为是
        String hashKey= resourceId + ":" + userId;
        redisUtil.hset(RedisKey.RESOURCE_USER_ISCOLLECT,hashKey,1);
        return Response.success().message("收藏成功");
 
    }


    @PostMapping("resource/uncollect")
    @ApiOperation(value="取消收藏")
    @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)
    public Response uncollect(HttpServletRequest request,@RequestParam(value= "resourceId")Long resourceId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        //向redis写入一条记录或者减1
        if(!redisUtil.hHasKey(RedisKey.RESOURCE_COLLECT_COUNT,resourceId.toString())){
            redisUtil.hset(RedisKey.RESOURCE_COLLECT_COUNT,resourceId.toString(),-1);
        }else{
            redisUtil.hdecr(RedisKey.RESOURCE_COLLECT_COUNT,resourceId.toString(),1);
        }
        //资源-用户-是否收藏设为否
        String hashKey= resourceId + ":" + userId;
        redisUtil.hset(RedisKey.RESOURCE_USER_ISCOLLECT,hashKey,0);
        return Response.success().message("取消收藏成功");
    }


    @GetMapping("resource/getResourceByPCode")
    @ApiOperation(value="按星球获取资源")
    @ApiImplicitParam(name="planetCode",value="星球id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)
    public Response getResourceByPCode(HttpServletRequest request,@RequestParam(value = "planetCode") Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        redisService.persistResourceLikeCount();
        redisService.persistResourceCollectCount();
        redisService.persistResourceUserIsCollect();
        redisService.persistResourceUserIsLike();
        List<ResourceVO> resourceVOList = resourceService.getResourceByPCode(planetCode,userId);
//        for (ResourceVO resourceVO : resourceVOList) {
//            String hashKey= resourceVO.getResourceId() + ":" + userId;
//            if(redisUtil.hHasKey(RedisKey.RESOURCE_USER_ISLIKE,hashKey)){
//                Boolean is_like=redisUtil.hget(RedisKey.RESOURCE_USER_ISLIKE,hashKey).equals(1);
//                resourceVO.setLiked(is_like);
//            }else{
//                resourceVO.setLiked(false);
//            }
//            if(redisUtil.hHasKey(RedisKey.RESOURCE_USER_ISCOLLECT,hashKey)){
//                Boolean is_collect=redisUtil.hget(RedisKey.RESOURCE_USER_ISCOLLECT,hashKey).equals(1);
//                resourceVO.setCollected(is_collect);
//            }else{
//                resourceVO.setCollected(false);
//            }
//        }
        return Response.success().message("查找成功").data("resourceList",resourceVOList);
    }


    @GetMapping("resource/getCollectResource")
    @ApiOperation(value="获取所有收藏的资源")
    public Response getCollectResource(HttpServletRequest request){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        //提前持久化一次
        redisService.persistResourceUserIsCollect();
        List<ResourceVO> resourceVOList = resourceService.getCollectResourceByUserId(userId);
        for (ResourceVO resourceVO : resourceVOList) {
            String hashKey= resourceVO.getResourceId() + ":" + userId;
            if(redisUtil.hHasKey(RedisKey.RESOURCE_USER_ISCOLLECT,hashKey)){
                Boolean is_like=redisUtil.hget(RedisKey.RESOURCE_USER_ISCOLLECT,hashKey).equals(1);
                resourceVO.setLiked(is_like);
            }else{
                resourceVO.setLiked(false);
            }
        }
        return Response.success().data("resourceList",resourceVOList).message("获取成功");
    }



}
