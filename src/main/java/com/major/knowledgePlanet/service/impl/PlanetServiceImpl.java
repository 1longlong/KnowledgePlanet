package com.major.knowledgePlanet.service.impl;


import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.mapper.PlanetMapper;
import com.major.knowledgePlanet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetServiceImpl implements PlanetService {
    @Autowired
    private PlanetMapper planetMapper;

    @Override
    public List<Planet> getHotestPlanet(){return planetMapper.getHotestPlanet();}

}
