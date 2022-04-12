package com.major.knowledgePlanet.controller;


import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @GetMapping("planet/getHotPlanet")
    public Response getHotPlanet (){

        List<Planet> planetList = planetService.getHotestPlanet();
        if(!planetList.isEmpty()){
            return Response.success().message("查找成功").data("planetList" ,planetList);
        }else{
            return Response.success().message("未查到热度信息");
        }
    }
}
