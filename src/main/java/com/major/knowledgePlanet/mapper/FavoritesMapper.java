package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Favorites;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FavoritesMapper {
    Integer addFavorites(Favorites favorites);

    Integer deleteFavorites(@Param("u_id")Long u_id , @Param("r_id") Long r_id);

    List<Favorites> getAll(Long u_id);
}
