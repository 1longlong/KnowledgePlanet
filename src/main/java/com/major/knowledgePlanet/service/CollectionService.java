package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Collection;

public interface CollectionService {
    Integer addCollection(Collection collection);

    Integer deleteCollection(Long u_id ,Long r_id);
}
