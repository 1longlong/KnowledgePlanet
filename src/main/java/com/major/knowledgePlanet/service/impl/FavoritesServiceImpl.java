package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Favorites;
import com.major.knowledgePlanet.mapper.FavoritesMapper;
import com.major.knowledgePlanet.service.FavoritesService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cj
 * @date 2022/4/13 16:42
 */
@Service
public class FavoritesServiceImpl implements FavoritesService {
    @Autowired
    private FavoritesMapper favoritesMapper;

    @Override
    public Integer addFavorites(Favorites favorites){return favoritesMapper.addFavorites(favorites);}

    @Override
    public  Integer  deleteFavorites(@Param("userId")Long userId , @Param("resourceId") Long resourceId){return favoritesMapper.deleteFavorites(userId,resourceId);}
    @Override
    public List<Favorites> getAll(Long u_id){return favoritesMapper.getAll(u_id);}
}
