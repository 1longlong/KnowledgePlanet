package
        com.major.knowledgePlanet.controller;

import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.Comment;
import com.major.knowledgePlanet.entity.Topic;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.CommentService;
import com.major.knowledgePlanet.service.TopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO:此处写TopicContoller类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/26 17:14
 */
@RestController
public class TopicController {
    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="topicServiceImpl")
    private TopicService topicService;

    @Resource(name="commentServiceImpl")
    private CommentService commentService;


    @PostMapping("topic/insertTopic")
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

    @GetMapping("topic/getAllTopic/{planetCode}")
    @ApiOperation(value = " 获取星球下所有帖子")
    public Response getAllTopic(@PathVariable("planetCode") Long planetCode){
        List<Topic> result= topicService.getAllTopic(planetCode);
        if(!result.isEmpty()){
            return Response.success().message("查找成功").data("result", result);
        }else{
            return  Response.success().message("未查询到相关信息");
        }
    }

    @GetMapping("topic/getCommentOfTopic/{topicId}")
    @ApiOperation(value = "获取topic下所有评论")
    public Response getCommentOfTopic(@PathVariable("topicId") Long topicId) {
        List<Comment> result = commentService.getCommentOfTopic(topicId);
        if(!result.isEmpty()){
            return Response.success().message("查找成功").data("result", result);
        }else{
            return  Response.success().message("未查询到相关信息");
        }
    }
}
