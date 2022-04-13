package com.major.knowledgePlanet.controller;



import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.result.Response;


import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.service.FavoritesService;

import com.major.knowledgePlanet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.Date;
import java.util.List;


/**
 * 包含资源管理基本功能
 *
 * @author cj
 * @date 2022/4/8 20:19
 */
@RestController
public class
ResourceController {
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private FavoritesService favoritesService;



    //TODO 加details
    @PostMapping("resource/uploadResource")
    public Response uploadResource(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "p_code") Long p_code,
                                   @RequestParam(value = "r_name") String r_name, @RequestParam(value = "link") String link,
                                   @RequestParam(value = "coverage") String coverage, @RequestParam(value = "r_description") String r_description ,
                                   @RequestParam(value = "details")String details){

        Date currentTime = new Date();
        Resource resource = new Resource();
        resource.setUserId(u_id);
        resource.setPlanetCode(p_code);
        resource.setResourceName(r_name);
        resource.setLink(link);
        resource.setDetails(details);
        resource.setUploadTime(currentTime);
        resource.setCoverage(coverage);
        resource.setStatus(0);
        resource.setResourceDescription(r_description);
        int result = resourceService.uploadResource(resource);
        if(result!=0){
            return Response.success().message("上传成功").data("r_id",resource.getResourceId()).data("upload_time",resource.getUploadTime());
        }else{
            return Response.serverError().message("上传失败").data("result",result);
        }


    }


    //点赞
    @PostMapping("resource/praise")
    public Response praise(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
        Resource resource = resourceService.getResourceById(r_id);
        int count =resource.getLikeCount()+1;
        resource.setLikeCount(count);
        Integer result = resourceService.upDatePraise(resource);

        if(result!=0) {
            return Response.success().message("点赞成功").data("result", result);
        }else{
            return Response.serverError().message("点赞失败").data("result",result);
        }

    }

    //取消点赞
    @PostMapping("resource/unPraise")
    //TODO 数据库创建用户和资源关系表
    public Response unPraise(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
        Resource resource = resourceService.getResourceById(r_id);
        int count = resource.getLikeCount()-1;
        if(count>0) {
            resource.setLikeCount(count);
            Integer result = resourceService.upDatePraise(resource);
            return Response.success().message("取消点赞成功").data("result", result);
        }
        else{
            return Response.clientError().message("取消点赞失败");
        }
    }

    //添加收藏夹
    @PostMapping("resource/collect")
    public Response collect(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
        Favorites favorites = new Favorites();
        favorites.setUserId(u_id);
        favorites.setResourceId(r_id);
        favorites.setIsCollected(false);

        Integer result = favoritesService.addFavorites(favorites);

        if(result!=0) {
            return Response.success().message("收藏成功").data("result", result);
        }else{
            return Response.serverError().message("收藏失败").data("result",result);
        }
    }

    //取消收藏
    @PostMapping("resource/uncollect")
    public Response uncollect(@RequestParam(value = "u_id")Long u_id ,@RequestParam(value= "r_id")Long r_id){
        Integer result = favoritesService.deleteFavorites(u_id, r_id);
        if(result!=0){
            return  Response.success().message("取消收藏成功").data("result",result);
        }else{
            return Response.serverError().message("取消收藏失败").data("result",result);
        }
    }

    @GetMapping("resource/getResourceById")
    public Response getResourceById(@RequestParam(value = "r_id") Long r_id){
        Resource resource = resourceService.getResourceById(r_id);
        return Response.success().message("查找成功").data("result",resource);
    }

    @GetMapping("resource/getResourceByPCode")
    public Response getResourceByPCode(@RequestParam(value = "p_code") Long p_code){
        List<Resource> resource = resourceService.getResourceByPCode(p_code);
        return Response.success().message("查找成功").data("result",resource);
    }




}
