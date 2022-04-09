package com.major.knowledgePlanet.controller;

import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class FavoritesController {
    @Autowired
    private FavoritesService favoritesService;

    @GetMapping("getAll")
    public List<Favorites> getAll(){
        // 顾名思义 实体和数据 同时返回页面和数据
        List<Favorites> favorites = favoritesService.getAll();
        return favorites;
    }

}
