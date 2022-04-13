package com.major.knowledgePlanet.controller;



import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.result.Response;


import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.service.FavoritesService;

import com.major.knowledgePlanet.service.ResourceService;
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
    private ResourceService resourceService;

    @Autowired
    private FavoritesService favoritesService;



    //TODO 加details
    @PostMapping("resource/uploadResource")
    @ApiOperation(value="上传资源")
    public Response uploadResource(HttpServletRequest request, @RequestBody Resource resource){

        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
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
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");

    }


    @PostMapping("resource/praise")
    @ApiOperation(value="点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    public Response praise(HttpServletRequest request, @RequestParam(value = "resourceId") Long resourceId){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);

            //TODO 用户资源关系表里插数据
            Resource resource = resourceService.getResourceById(resourceId);
            int count = resource.getLikeCount() + 1;
            resource.setLikeCount(count);
            Integer result = resourceService.upDatePraise(resource);

            if (result != 0) {
                return Response.success().message("点赞成功").data("result", result);
            } else {
                return Response.serverError().message("点赞失败").data("result", result);
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    //取消点赞
    @PostMapping("resource/unPraise")
    @ApiOperation(value="取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    //TODO 数据库创建用户和资源关系表
    public Response unPraise(HttpServletRequest request, @RequestParam(value = "resourceId") Long resourceId){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            //TODO 用户资源关系表插数据
            Resource resource = resourceService.getResourceById(resourceId);
            int count = resource.getLikeCount() - 1;
            if (count > 0) {
                resource.setLikeCount(count);
                Integer result = resourceService.upDatePraise(resource);
                return Response.success().message("取消点赞成功").data("result", result);
            } else {
                return Response.clientError().message("取消点赞失败");
            }

        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    //添加收藏夹
    @PostMapping("resource/collect")
    @ApiOperation(value="添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    public Response collect(HttpServletRequest request,@RequestParam(value = "resourceId") Long resourceId){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            Favorites favorites = new Favorites();
            favorites.setUserId(userId);
            favorites.setResourceId(resourceId);
            favorites.setIsCollected(false);

            Integer result = favoritesService.addFavorites(favorites);

            if (result != 0) {
                return Response.success().message("收藏成功").data("result", result);
            } else {
                return Response.serverError().message("收藏失败").data("result", result);
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
 
    }

    //取消收藏
    @PostMapping("resource/uncollect")
    @ApiOperation(value="取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    public Response uncollect(HttpServletRequest request,@RequestParam(value= "resourceId")Long resourceId){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            Integer result = favoritesService.deleteFavorites(userId, resourceId);
            if (result != 0) {
                return Response.success().message("取消收藏成功").data("result", result);
            } else {
                return Response.serverError().message("取消收藏失败").data("result", result);
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    @GetMapping("resource/getResourceById")
    @ApiOperation(value="按资源id获取资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceId",value="资源id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    public Response getResourceById(@RequestParam(value = "resourceId") Long resourceId){
        Resource resource = resourceService.getResourceById(resourceId);
        return Response.success().message("查找成功").data("result",resource);
    }

    @GetMapping("resource/getResourceByPCode")
    @ApiOperation(value="按星球获取资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name="planetCode",value="星球id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)})
    public Response getResourceByPCode(@RequestParam(value = "planetCode") Long planetCode){
        List<Resource> resource = resourceService.getResourceByPCode(planetCode);
        if(!resource.isEmpty()){
            return Response.success().message("查找成功").data("result",resource);
        }else{
            return Response.serverError().message("未查到相关信息");
        }
    }


    @GetMapping("getAll")
    @ApiOperation(value="获取所有资源")
    public Response getAll(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            List<Favorites> favorites = favoritesService.getAll(userId);
            if (!favorites.isEmpty()) {
                return Response.success().message("查找成功").data("favorites", favorites);
            } else {
                return Response.serverError().message("未找到相关信息");
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }



}
