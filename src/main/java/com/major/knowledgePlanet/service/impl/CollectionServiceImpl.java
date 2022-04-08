package com.major.knowledgePlanet.service.impl;


import com.major.knowledgePlanet.entity.Collection;
import com.major.knowledgePlanet.mapper.CollectionMapper;
import com.major.knowledgePlanet.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cj
 * @date 2022/4/8 22:00
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public Integer addCollection(Collection collection){return collectionMapper.addCollection(collection);}

    @Override
    public  Integer  deleteCollection(Long u_id ,Long r_id){return collectionMapper.deleteCollection(u_id,r_id);}

}
