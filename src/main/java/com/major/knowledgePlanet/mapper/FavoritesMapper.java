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

    Integer deleteFavorites(@Param("userId")Long userId , @Param("resourceId") Long resourceId);

    List<Favorites> getAll(Long u_id);
}
