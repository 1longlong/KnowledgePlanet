package
        com.major.knowledgePlanet.controller;

import cn.hutool.jwt.JWTUtil;
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

    @Resource(name="topicServiceImpl")
    private TopicService topicService;

    @Resource(name="commentServiceImpl")
    private CommentService commentService;


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
    @ApiOperation(value="获取推荐星球")
    public Response getRecommendPlanet(HttpServletRequest request){
        Long userId=TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
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






    @PostMapping("planet/insertTopic")
    @ApiOperation(value = "发帖功能")
    public Response insertTopic(HttpServletRequest request , @RequestBody Topic topic){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);

            Integer result = topicService.insertTopic(topic , userId);
            if(result!=0){
                return Response.success().message("发帖成功").data("topicId",topic.getTopicId()).data("createTime",topic.getTime());
            }else{
                return Response.serverError().message("发帖失败");
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    @GetMapping("planet/getAllTopic")
    @ApiOperation(value = " 获取星球下所有帖子")
    public Response getAllTopic(Long planetCode){
        List<Topic> result= topicService.getAllTopic(planetCode);
        if(!result.isEmpty()){
            return Response.success().message("查找成功").data("result", result);
        }else{
            return  Response.success().message("未查询到相关信息");
        }
    }

    @GetMapping("planet/getCommentOfTopic")
    @ApiOperation(value = "获取topic下所有评论")
    public Response getCommentOfTopic(Long topicId) {
        List<Comment> result = commentService.getCommentOfTopic(topicId);
        if(!result.isEmpty()){
            return Response.success().message("查找成功").data("result", result);
        }else{
            return  Response.success().message("未查询到相关信息");
        }
    }
}
