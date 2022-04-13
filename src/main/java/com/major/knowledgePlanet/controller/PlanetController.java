package
        com.major.knowledgePlanet.controller;

import com.major.knowledgePlanet.entity.Planet;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.PlanetService;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO:此处写PlanetController类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/11 16:31
 */
@Api(tags="星球模块",value="PlanetController")
@RestController
public class PlanetController {

    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="planetServiceImpl")
    private PlanetService planetService;


    @PostMapping("planet/createPlanet")
    @ApiOperation(value="创建星球")
    @ApiImplicitParams({
            @ApiImplicitParam(name="planetName",value="星球名称",dataType = "String",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="planetDescription",value="星球介绍",dataType = "String",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="planetAvatar",value="星球头像",dataType = "String",dataTypeClass = String.class,paramType = "query",required = false)})
    public Response createPlanet(HttpServletRequest request, @RequestParam("planetName")String planetName, @RequestParam("planetDescription")String planetDescription,
                              @RequestParam(value = "planetAvatar",required = false)String planetAvatar){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId!=null){
            Long planetCode= planetService.createPlanet(userId,planetName,planetDescription,planetAvatar);
            if(planetCode!=null){
                return Response.success().data("planetCode",planetCode);
            }
            return Response.serverError().message("创建失败！");
        }
        return Response.clientError().code("B0201").message("token验证失败，请重新登录");
    }


    @GetMapping("planet/searchPlanet")
    @ApiOperation(value="根据关键词查询星球")
    @ApiImplicitParam(name="keyWord",value = "搜索的关键词",dataType="String",dataTypeClass = String.class,paramType = "query",required = true)
    public Response searchPlanet(@RequestParam("keyWord")String keyWord){
        List<Planet> planetList = planetService.searchPlanet(keyWord);
        return Response.success().data("planetList",planetList);
    }


    @GetMapping("planet/getRecommendPlanet")
    public Response getRecommendPlanet(HttpServletRequest request){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        //TODO: 获得推荐的星球
        List<Planet> planetList=planetService.getRecommendPlanet(userId);
        return Response.success().data("planetList",planetList);
    }

    @GetMapping("planet/getHotPlanet")
    public Response getHotPlanet (){

        List<Planet> planetList = planetService.getHotestPlanet();
        if(!planetList.isEmpty()){
            return Response.success().message("查找成功").data("planetList" ,planetList);
        }else{
            return Response.success().message("未查到热度信息");
        }
    }
}
