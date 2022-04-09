package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Favorites;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FavoritesMapper {
    Integer addFavorites(Favorites favorites);

    Integer deleteFavorites(Long u_id ,Long r_id);
}
