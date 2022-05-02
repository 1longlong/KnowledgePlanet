package
        com.major.knowledgePlanet.controller;

import com.major.knowledgePlanet.VO.ActivityVO;
import com.major.knowledgePlanet.entity.Activity;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.ActivityService;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("activity/getActivity")
    @ApiOperation(value="获取星球中的所有活动以及和登录用户的关系")
    @ApiImplicitParam(name="星球id",value="planetCode",dataType = "Long",dataTypeClass = Long.class,paramType = "query",required = true)
    public Response getActivity(HttpServletRequest request,@RequestParam("planetCode") Long planetCode){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        List<ActivityVO> activityList = activityService.getActivity(userId, planetCode);
        return Response.success().data("activityList",activityList);
    }








}
