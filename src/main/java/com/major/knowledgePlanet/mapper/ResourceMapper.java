package com.major.knowledgePlanet.mapper;


import com.major.knowledgePlanet.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ResourceMapper {

    Integer uploadResource (Resource resource);
}
