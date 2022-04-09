package com.major.knowledgePlanet.controller;

import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginLogController {

    @Autowired
    LoginLogService loginLogService;

    @GetMapping("getLoginLogById")
    public Response getLoginLogById(@RequestParam("u_id") Long u_id){
        List<LoginLog> loginLogList = loginLogService.getLoginLogById(u_id);
        if(!loginLogList.isEmpty()){
             return Response.success().message("查找成功").data("result",loginLogList);
        }else{
            return Response.serverError().message("未查询到相关结果").data("result",loginLogList);
        }

    }
}
