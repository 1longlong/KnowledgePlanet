package
        com.major.knowledgePlanet.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.major.knowledgePlanet.VO.ActivityVO;
import com.major.knowledgePlanet.entity.Activity;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.ActivityService;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO:此处写ActivityController类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/1 20:59
 */
@RestController
@Api(tags="活动拼团模块",value="ActivityController")
public class ActivityController {

    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="activityServiceImpl")
    private ActivityService activityService;




    @PostMapping("activity/addActivity")
    @ApiOperation(value="创建活动")
    public Response addActivity(HttpServletRequest request, @RequestBody Activity activity){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        activityService.addActivity(activity,userId);
        return Response.success().message("创建成功");
    }

    @GetMapping("activity/getActivity/{planetCode}")
    @ApiOperation(value="获取星球中的所有活动以及和登录用户的关系")
    @ApiImplicitParam(name="星球id",value="planetCode",dataType = "Long",dataTypeClass = Long.class,paramType = "path",required = true)
    public Response getActivity(HttpServletRequest request,@PathVariable("planetCode") Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        List<ActivityVO> activityList = activityService.getActivity(userId, planetCode);
        return Response.success().data("activityList",activityList);
    }


    @PostMapping("activity/cancelActivity")
    @ApiOperation(value="取消活动")
    @ApiImplicitParam(name="活动id",value="activityId",dataType ="Long",dataTypeClass = Long.class,paramType = "query",required = true)
    public Response cancelActivity(@RequestParam("activityId")Long activityId){
        activityService.cancelActivity(activityId);
        return Response.success().message("取消成功");
    }


    @PostMapping("activity/checkActivity")
    @ApiOperation(value = "审核活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name="活动id",value="activityId",dataType ="Long",dataTypeClass = Long.class,paramType = "query",required = true),
            @ApiImplicitParam(name="审核信息",value = "checkInfo",dataType = "String",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="审核结果，1表示通过，2表示拒绝",value="checkResult",dataType = "Integer",dataTypeClass = Integer.class,paramType = "query",required = true)
    })
    public Response checkActivity(HttpServletRequest request,@RequestParam("activityId")Long activityId,@RequestParam("checkInfo")String checkInfo,@RequestParam("checkResult")Integer checkResult){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        try {
            activityService.checkActivity(userId,activityId,checkInfo,checkResult);
            return Response.success().message("审核成功");
        }catch(Exception e){
            return Response.serverError().message(e.getMessage());
        }
    }

    @PostMapping("activity/joinOrQuitActivity")
    @ApiOperation(value="参加活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name="活动id",value="activityId",dataType = "Long",dataTypeClass = Long.class,paramType = "query",required = true),
            @ApiImplicitParam(name="参加或者取消,1表示参加，0表示取消",value="type",dataType = "Integer",dataTypeClass = Integer.class,paramType = "query",required = true)
    })
    public Response joinOrQuitActivity(HttpServletRequest request,@RequestParam("activityId")Long activityId,@RequestParam("type")Integer type){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        try{
            activityService.joinOrQuitActivity(activityId,userId,type);
            return Response.success().message("操作成功!");
        }catch (Exception e){
            e.printStackTrace();
            return Response.clientError().message(e.getMessage());
        }
    }

    @GetMapping("activity/getActivityNum")
    @ApiOperation(value="统计创建和参与的活动数量")
    public Response getActivityNum(HttpServletRequest request){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        Integer createNum = activityService.getActivityNum(userId, 1);
        Integer joinNum=activityService.getActivityNum(userId,0);
        return Response.success().data("createNum",createNum).data("joinNum",joinNum);
    }

    @GetMapping("activity/getActivityMember/{activityId}")
    @ApiOperation(value="获取报名活动的成员")
    public Response getActivityMember(@PathVariable("activityId")Long activityId){
        List<User> memberList = activityService.getActivityMember(activityId);
        return Response.success().data("memberList",memberList);
    }







}
