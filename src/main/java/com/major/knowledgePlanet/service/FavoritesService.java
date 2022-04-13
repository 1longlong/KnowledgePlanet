package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Favorites;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface FavoritesService {
    Integer addFavorites(Favorites favorites);

    Integer deleteFavorites(@Param("userId")Long userId , @Param("resourceId") Long resourceId);

    List<Favorites> getAll(Long u_id);
}
