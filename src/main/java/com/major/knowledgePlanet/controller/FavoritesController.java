package com.major.knowledgePlanet.controller;

import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.service.FavoritesService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class FavoritesController {
    @Autowired
    private FavoritesService favoritesService;

    @GetMapping("getAll")
    public List<Favorites> getAll(@RequestParam(value="u_id") Long u_id){
        List<Favorites> favorites = favoritesService.getAll(u_id);
        return favorites;
    }

}
