package
        com.major.knowledgePlanet.controller;

import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.VO.SearchResultVO;
import com.major.knowledgePlanet.entity.*;
import com.major.knowledgePlanet.mapper.TopicMapper;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.CommentService;
import com.major.knowledgePlanet.service.PlanetService;
import com.major.knowledgePlanet.service.TopicService;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

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
    public Response searchPlanet(HttpServletRequest request,@RequestParam("keyWord")String keyWord){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        List<SearchResultVO> planetList = planetService.searchPlanet(keyWord,userId);
        return Response.success().data("planetList",planetList);
    }

    @GetMapping("planet/adminSearchPlanet")
    @ApiOperation(value="管理员根据关键词查询星球")
    public Response adminSearchPlanet(@RequestParam("keyWord")String keyWord){
        List<Planet> planetList = planetService.adminSearchPlanet(keyWord);
        return Response.success().data("planetList",planetList);
    }



    @GetMapping("planet/getRecommendPlanet")
    @ApiOperation(value="获取推荐星球")
    public Response getRecommendPlanet(HttpServletRequest request){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        System.out.println(userId);
        //TODO: 获得推荐的星球
        List<RecommendPlanetVO> planetList=planetService.getRecommendPlanet(userId);
        return Response.success().data("planetList",planetList);
    }

    @GetMapping("planet/getHotPlanet")
    @ApiOperation(value="按热度获取前6个星球")
    public Response getHotPlanet (){

        List<Planet> planetList = planetService.getHotestPlanet();
        if(!planetList.isEmpty()){
            return Response.success().message("查找成功").data("planetList" ,planetList);
        }else{
            return Response.success().message("未查到热度信息");
        }
    }

    @GetMapping("planet/getMemNumOfPlanet")
    @ApiOperation(value = "根据星球id获取用户人数")
    public Response getMemNumber(Long planetCode){
        Integer result = planetService.getMemNumOfPlanet(planetCode);
        if(result!=null) {
            return Response.success().message("查找成功").data("num", result);
        }else{
            return Response.serverError().message("查找失败");
        }
    }

    @GetMapping("planet/getPlanet/{role}")
    @ApiOperation(value="获取用户创建或加入的星球及积分")
    @ApiImplicitParam(name="role",value="查询的星球类型",dataType="Integer",dataTypeClass = Integer.class,paramType = "path",required = true)
    public Response getPlanet(HttpServletRequest request,@PathVariable("role")Integer role){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        //查创建的星球，加入的星球
        if(role!=1&&role!=2){
            return Response.clientError().code("A0304").message("参数错误");
        }
        List<UserPlanetVO> userPlanetVOList = planetService.getPlanet(userId, role);
        return Response.success().data("planetList",userPlanetVOList);
    }


    @PostMapping("planet/joinPlanet")
    @ApiOperation(value="加入星球")
    @ApiImplicitParam(name="planetCode",value = "星球号",dataType="Long",dataTypeClass = Long.class,paramType = "query",required = true)
    public Response joinPlanet(HttpServletRequest request,@RequestParam("planetCode")Long planetCode){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        try {
            planetService.joinPlanet(userId,planetCode);
            return Response.success().message("加入成功！");
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return Response.clientError().message("已加入该星球");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().message("加入失败");
        }
    }

    @GetMapping("planet/getLeaderboard/{planetCode}")
    public Response getLeaderboard(HttpServletRequest request,@PathVariable("planetCode") Long planetCode){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        JSONObject obj = planetService.getLeaderboard(userId, planetCode);
        return Response.success().data("result",obj);


    }


    @GetMapping("planet/getMember")
    @ApiOperation(value ="查找星球内成员列表")
    @ApiImplicitParam(name="planetCode",value="星球号" ,dataType="Long",paramType = "query" ,required = true)
    public Response getMember(@RequestParam("planetCode")Long planetCode){
        List <User> result = planetService.getMemberListOfPlanet(planetCode);
        if(!result.isEmpty()){
            return Response.success().message("查找成功").data("result",result);
        }else{
            return Response.success().message("星球暂无成员");
        }
    }

    @PostMapping("planet/deleteMember")
    @ApiOperation(value = "踢出成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="planetCode",value="星球编号",dataType = "Long",dataTypeClass = String.class,paramType = "query",required = true)
    })
    public Response deleteMember(@RequestParam("userId")Long userId ,@RequestParam("planetCode") Long planetCode){
        Integer result = planetService.deleteMember(userId,planetCode);
        if(result!=0){
            return Response.success().message("踢出成功").data("result",result);
        }else{
            return Response.serverError().message("删除失败");
        }
    }

    @GetMapping("planet/getRole/{planetCode}")
    @ApiOperation(value="获取用户在星球中的身份")
    @ApiImplicitParam(name="planetCode",value="星球id",dataType = "Long",dataTypeClass = Long.class,paramType = "path",required = true)
    public Response getRole(HttpServletRequest request,@PathVariable("planetCode")Long planetCode){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        Integer role = planetService.getRole(userId, planetCode);
        return Response.success().data("role",role);
    }
    @GetMapping("planet/getAllPlanet")
    @ApiOperation(value="获取所有的星球")
    public Response getAllPlanet(){
        List<Planet> planetList = planetService.getAllPlanet();
        return Response.success().data("planetList",planetList);
    }

    @PostMapping("planet/quitPlanet")
    @ApiOperation(value="退出星球")
    public Response quitPlanet(HttpServletRequest request,@RequestParam("planetCode")Long planetCode){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        planetService.deleteMember(userId,planetCode);
        return Response.success().message("退出成功");
    }

    @GetMapping("planet/getOwnerId/{planetCode}")
    @ApiOperation(value="获取星主id")
    public Response getOwnerId(@PathVariable("planetCode")Long planetCode){
        Long ownerId = planetService.getOwnerId(planetCode);
        return Response.success().data("ownerId",ownerId);
    }






}
