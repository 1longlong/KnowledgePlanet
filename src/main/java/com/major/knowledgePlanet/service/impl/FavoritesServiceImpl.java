package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.mapper.FavoritesMapper;
import com.major.knowledgePlanet.service.FavoritesService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService {
    @Autowired
    private FavoritesMapper favoritesMapper;

    @Override
    public Integer addFavorites(Favorites favorites){return favoritesMapper.addFavorites(favorites);}

    @Override
    public  Integer  deleteFavorites(@Param("u_id")Long u_id , @Param("r_id") Long r_id){return favoritesMapper.deleteFavorites(u_id,r_id);}

    @Override
    public List<Favorites> getAll(Long u_id){return favoritesMapper.getAll(u_id);}
}
