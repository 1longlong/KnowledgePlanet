package com.major.knowledgePlanet.controller;



import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.result.Response;


import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.service.FavoritesService;

import com.major.knowledgePlanet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.Date;


/**
 * 包含资源管理基本功能
 *
 * @author cj
 * @date 2022/4/8 20:19
 */
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private FavoritesService favoritesService;


    @PostMapping("resource/uploadResource")
    public Response uploadResource(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "p_code") Long p_code,
                                   @RequestParam(value = "u_name") String u_name,@RequestParam(value = "r_name") String r_name,
                                   @RequestParam(value = "link") String link,@RequestParam(value = "coverage") String coverage,
                                   @RequestParam(value = "r_description") String r_description){

        Date currentTime = new Date();
        Resource resource = new Resource();
        resource.setU_id(u_id);
        resource.setP_code(p_code);
        resource.setU_name(u_name);
        resource.setR_name(r_name);
        resource.setLink(link);
        resource.setUpload_time(currentTime);
        resource.setCoverage(coverage);
        resource.setR_description(r_description);
        int result = resourceService.uploadResource(resource);
        if(result!=0){
            return Response.success().message("上传成功").data("r_id",resource.getR_id());
        }else{
            return Response.serverError().message("上传失败").data("result",result);
        }


    }

    //点赞
    @PostMapping("resource/praise")
    public Response praise(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
        Resource resource = resourceService.getResourceById(r_id);
        int count =resource.getPraise_count()+1;
        resource.setPraise_count(count);
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
        int count = resource.getPraise_count()-1;
        if(count>0) {
            resource.setPraise_count(count);
            Integer result = resourceService.upDatePraise(resource);
            return Response.success().message("取消点赞成功").data("result", result);
        }
        else{
            return Response.clientError().message("取消点赞失败");
        }
    }

    //添加收藏夹
    @PostMapping("resource/collect")
    public Response collect(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id,
                            @RequestParam(value = "r_name") String r_name,@RequestParam(value="coverage") String coverage){
        Favorites favorites = new Favorites();
        favorites.setU_id(u_id);
        favorites.setR_id(r_id);
        favorites.setCoverage(coverage);
        favorites.setR_name(r_name);

        Integer result = favoritesService.addFavorites(favorites);

        if(result!=0) {
            return Response.success().message("收藏成功").data("result", result);
        }else{
            return Response.serverError().message("收藏失败").data("result",result);
        }
    }

    //取消收藏
    @PostMapping("resource/uncollect")
    public Response uncollect(@RequestParam(value = " u_id")Long u_id ,@RequestParam(value= "r_id")Long r_id){
        Integer result = favoritesService.deleteFavorites(u_id, r_id);
        if(result!=0){
            return  Response.success().message("取消收藏成功").data("result",result);
        }else{
            return Response.serverError().message("取消收藏失败").data("result",result);
        }
    }



}
