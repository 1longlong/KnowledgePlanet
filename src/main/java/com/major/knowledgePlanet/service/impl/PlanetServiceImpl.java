package
        com.major.knowledgePlanet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.VO.SearchResultVO;
import com.major.knowledgePlanet.constValue.UserPlanetEnum;
import com.major.knowledgePlanet.entity.*;
import com.major.knowledgePlanet.mapper.PlanetMapper;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.PlanetService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Planet> adminSearchPlanet(String info){
        return planetMapper.adminSearchPlanet(info);
    }

    @Override
    public List<SearchResultVO> searchPlanet(String info,Long userId) {
        return planetMapper.searchPlanet(info,userId);
    }

    @Override
    public List<RecommendPlanetVO> getRecommendPlanet(Long userId) {

        return planetMapper.getRecommendPlanet(userId);
    }

    @Override
    public List<Planet> getHotestPlanet(){return planetMapper.getHotestPlanet();   }

    @Override
    public Integer getMemNumOfPlanet(Long planetCode){
        Integer result = planetMapper.getMemNumOfPlanet(planetCode);
        System.out.println(result);
        return result;
    }

    @Override
    public List<UserPlanetVO> getPlanet(Long userId, Integer role) {
        return planetMapper.getPlanet(userId,role);
    }

    @Override
    public void joinPlanet(Long userId, Long planetCode) throws Exception {
        UserPlanetRel userPlanetRel = new UserPlanetRel();
        userPlanetRel.setUserId(userId);
        userPlanetRel.setPlanetCode(planetCode);
        userPlanetRel.setRole(UserPlanetEnum.COMMON_MEMBER.getRole());
        userPlanetRel.setIntegral(0);
        try{
            planetMapper.addUserPlanetRel(userPlanetRel);
        }
        catch(DuplicateKeyException e){
            throw new DuplicateKeyException(e.getMessage());
        };
    }

    @Override
    public JSONObject getLeaderboard(Long userId, Long planetCode) {
        Integer integral = planetMapper.getIntegral(userId, planetCode);
        JSONObject object = new JSONObject();
        object.put("integral", integral);
        Integer rank = null;
        int count = 1;
        List<Map<String, Object>> userList = planetMapper.getUserRank(planetCode);
        for (Map<String, Object> stringObjectMap : userList) {
            if (stringObjectMap.get("userId").equals(userId)) {
                rank = count;
            }
            stringObjectMap.put("rank", count);
            count++;
        }
        object.put("rank", rank);
        object.put("userList", userList);
        return object;
    }
    @Override
    public List<User> getMemberListOfPlanet(Long planetId){
        return planetMapper.getMemberListOfPlanet(planetId);
    }

    @Override
    public  Integer deleteMember(Long userId ,Long planetCode){
        return planetMapper.deleteMember(userId ,planetCode);
    }

    @Override
    public Integer getRole(Long userId, Long planetCode) {
        return planetMapper.getRole(userId, planetCode);
    }

    @Override
    public List<Planet> getAllPlanet() {
        return planetMapper.getAllPlanet();
    }

    @Override
    public void changeUserPlanetIntegral(Long userId, Long planetCode, Integer delta) {
        planetMapper.changeUserPlanetIntegral(userId,planetCode,delta);
    }

    @Override
    public Long getOwnerId(Long planetCode) {
        return planetMapper.getOwnerId(planetCode);
    }


}
