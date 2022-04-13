package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Favorites;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface FavoritesService {
    Integer addFavorites(Favorites favorites);

    Integer deleteFavorites(@Param("u_id")Long u_id ,@Param("r_id") Long r_id);

    List<Favorites> getAll(Long u_id);
}
