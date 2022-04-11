package com.major.knowledgePlanet.mapper;


import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResourceMapper {

    Integer uploadResource (Resource resource);

    Integer upDatePraise(Resource resource);

    Resource getResourceById(Long r_id);

    List<Resource> getResourceByPCode(Long p_code);


}
