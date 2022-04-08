package com.major.knowledgePlanet.controller;


import com.major.knowledgePlanet.entity.Resource;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("resource/uploadResource")
    public Response uploadResource(@RequestParam(value = "u_id") Long u_id,@RequestParam(value = "p_code") Long p_code,
                                  @RequestParam(value = "u_name") String u_name,@RequestParam(value = "r_name") String r_name,
                                  @RequestParam(value = "link") String link,@RequestParam(value = "coverage") String coverage,
                                  @RequestParam(value = "r_description") String r_description){
        Resource resource = new Resource();
        resource.setUId(u_id);
        resource.setPCode(p_code);
        resource.setUName(u_name);
        resource.setRName(r_name);
        resource.setLink(link);
        resource.setCoverage(coverage);
        resource.setRDescription(r_description);
        Integer result = resourceService.uploadResource(resource);
        return Response.success().message("上传成功").data("result",result);


    }



}
