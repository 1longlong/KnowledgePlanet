package com.major.knowledgePlanet.controller;

import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags="后台管理模块",value = "Background")
public class BackgroundController {
    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="userInfoServiceImpl")
    private UserInfoService userInfoService;

    @PostMapping("background/updateNameById")
    public Response updateNameById(HttpServletRequest request , @RequestParam(value = "newName") String newName){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            Integer result = userInfoService.updateNameByUserId(userId,newName);
            if(result!=0){
                return Response.success().message("修改成功").data("result",result);
            }else{
                return Response.serverError().message("修改失败").data("result",result);
            }

        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }



}
