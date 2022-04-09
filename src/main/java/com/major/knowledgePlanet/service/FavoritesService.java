package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Favorites;

public interface FavoritesService {
    Integer addFavorites(Favorites favorites);

    Integer deleteFavorites(Long u_id ,Long r_id);
}
