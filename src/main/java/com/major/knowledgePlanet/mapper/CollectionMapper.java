package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CollectionMapper {
    Integer addCollection(Collection collection);

    Integer deleteCollection(Long u_id ,Long r_id);

}
