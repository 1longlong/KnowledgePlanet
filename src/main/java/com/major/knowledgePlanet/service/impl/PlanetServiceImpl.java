package
        com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.constValue.UserPlanetEnum;
import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.entity.UserPlanetRel;
import com.major.knowledgePlanet.mapper.PlanetMapper;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * TODO:此处写PlanetServiceImpl类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/12 18:08
 */
@Service
public class PlanetServiceImpl implements PlanetService {

    @Value("${defaultPlanetAvatar}")
    private String defaultPlanetAvatar;

    @Value("${defaultPlanetMaxNumber}")
    private Integer defaultPlanetMaxNumber;

    @Resource(name="planetMapper")
    private PlanetMapper planetMapper;

    @Override
    public Integer addPlanet(Planet planet) {
        return planetMapper.addPlanet(planet);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createPlanet(Long userId,String planetName,String planetDescription,String planetAvatar) {
        Planet planet=new Planet();
        planetAvatar=planetAvatar==null?defaultPlanetAvatar:planetAvatar;
        planet.setCreateTime(new Date());
        planet.setPlanetName(planetName);
        planet.setPlanetDescription(planetDescription);
        planet.setMaxNumber(defaultPlanetMaxNumber);
        planet.setPlanetAvatar(planetAvatar);
        planet.setHot(0);
        if(planetMapper.addPlanet(planet)>0){
            UserPlanetRel userPlanetRel= new UserPlanetRel(userId,planet.getPlanetCode(), UserPlanetEnum.CREATER.getRole(),0);
            if(planetMapper.addUserPlanetRel(userPlanetRel)>0){
                return planet.getPlanetCode();
            }
        }
        return null;
    }
}
