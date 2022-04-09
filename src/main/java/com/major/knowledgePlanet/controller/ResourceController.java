package com.major.knowledgePlanet.controller;


<<<<<<< Updated upstream
import com.major.knowledgePlanet.entity.Collection;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.CollectionService;
=======
import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.FavoritesService;
>>>>>>> Stashed changes
import com.major.knowledgePlanet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< Updated upstream
=======
import java.text.SimpleDateFormat;
import java.util.Date;

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    private CollectionService collectionService;
=======
    private FavoritesService favoritesService;
>>>>>>> Stashed changes

    @PostMapping("resource/uploadResource")
    public Response uploadResource(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "p_code") Long p_code,
                                  @RequestParam(value = "u_name") String u_name,@RequestParam(value = "r_name") String r_name,
                                  @RequestParam(value = "link") String link,@RequestParam(value = "coverage") String coverage,
                                  @RequestParam(value = "r_description") String r_description){
<<<<<<< Updated upstream
=======
        Date currentTime = new Date();

>>>>>>> Stashed changes
        Resource resource = new Resource();
        resource.setU_id(u_id);
        resource.setP_code(p_code);
        resource.setU_name(u_name);
        resource.setR_name(r_name);
        resource.setLink(link);
<<<<<<< Updated upstream
        resource.setCoverage(coverage);
        resource.setR_description(r_description);
        Integer result = resourceService.uploadResource(resource);
        return Response.success().message("上传成功").data("result",result);
=======
        resource.setUpload_time(currentTime);
        resource.setCoverage(coverage);
        resource.setR_description(r_description);
        int result = resourceService.uploadResource(resource);
        if(result!=0){
            return Response.success().message("上传成功").data("r_id",resource.getR_id());
        }else{
            return Response.serverError().message("上传失败").data("result",result);
        }

>>>>>>> Stashed changes


    }

    //点赞
    @PostMapping("resource/praise")
    public Response praise(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
        Resource resource = resourceService.getResourceById(r_id);
        int count =resource.getPraise_count()+1;
        resource.setPraise_count(count);
        Integer result = resourceService.upDatePraise(resource);
<<<<<<< Updated upstream
        return Response.success().message("点赞成功").data("result",result);
=======
        if(result!=0) {
            return Response.success().message("点赞成功").data("result", result);
        }else{
            return Response.serverError().message("点赞失败").data("result",result);
        }
>>>>>>> Stashed changes
    }

    //取消点赞
    @PostMapping("resource/unPraise")
<<<<<<< Updated upstream
    public Response unPpraise(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
=======
    public Response unPraise(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "r_id") Long r_id){
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                            @RequestParam(value = "r_name") String r_name,@RequestParam(vaule="coverage") String coverage){
        Collection collection = new Collection();
        collection.setU_id(u_id);
        collection.setR_id(r_id);
        collection.setCoverage(coverage);
        collection.setR_name(r_name);

        Integer result = collectionService.addCollection(collection);
        return Response.success().message("收藏成功").data("result", result);
    }

    //取消收藏
    @PostMapping("resource/uncollect")
=======
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
//    @PostMapping("resource/uncollect")
>>>>>>> Stashed changes


}
