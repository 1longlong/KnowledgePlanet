package
        com.major.knowledgePlanet.controller;

import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.major.knowledgePlanet.entity.Reply;
import com.major.knowledgePlanet.entity.Topic;
import com.major.knowledgePlanet.entity.TopicVO;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.CommentService;
import com.major.knowledgePlanet.service.TopicService;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(tags="讨论区模块",value="TopicCOntroller")
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
        List<TopicVO> topicList = topicService.getAllTopic(planetCode);
        return Response.success().data("topicList",topicList);
    }


    @PostMapping("topic/addComment")
    @ApiOperation(value = "添加评论或回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name="topicId",value="所在topic的id",dataType="Long",dataTypeClass=Long.class,paramType="query",required=true),
            @ApiImplicitParam(name="parentId",value="上级评论id，若是对topic的评论则为null",dataType="Long",dataTypeClass = String.class,paramType = "query"),
            @ApiImplicitParam(name="content",value="评论内容",dataType="String",dataTypeClass = String.class,paramType ="query",required = true),
            @ApiImplicitParam(name="firstCommentId",value="以及评论id，若本身为一级评论则为null",dataType = "Long",dataTypeClass = Long.class,paramType = "query"),
            @ApiImplicitParam(name="type",value="评论对象的类型，1表示topic,0表示comment",dataType="Integer",dataTypeClass = Integer.class,paramType = "query",required = true)
    })
    public Response addComment(HttpServletRequest request,@RequestParam("topicId")Long topicId, @RequestParam(value = "parentId",required = false) Long parentId,@RequestParam("content") String content,@RequestParam("firstCommentId")Long firstCommentId,  @RequestParam("type")Integer type) {
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        try {
            if(type==1){
                commentService.addComment(userId,topicId,null,content,null);
            }else if(type==0){
                commentService.addComment(userId,topicId,parentId,content,firstCommentId);
            }else{
                return Response.clientError().message("参数有误");
            }
            return Response.success().message("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().message("添加失败");
        }
    }

    @GetMapping("topic/getFirstComment/{topicId}")
    @ApiOperation(value="获取话题的一级评论")
    @ApiImplicitParam(name="topicId",value="话题id",dataType="Long",dataTypeClass=Long.class,paramType = "path",required = true)
    public Response getFirstComment(HttpServletRequest request,@PathVariable("topicId")Long topicId){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        JSONObject result = commentService.getFirstCommentWithReplyCount(topicId,userId);
        return Response.success().data("result",result);
    }

    @GetMapping("topic/getAllReply/{commentId}")
    @ApiOperation(value="获取评论的所有回复")
    @ApiImplicitParam(name="commentId",value = "评论id",dataType = "Long",dataTypeClass = Long.class,paramType = "path",required = true)
    public Response getAllReply(@PathVariable("commentId")Long commentId){
        List<Reply> replyList = commentService.getAllReply(commentId);
        return Response.success().data("replyList",replyList);
    }

    @PostMapping("topic/praise")
    @ApiOperation(value="给话题或者评论点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="评论或者话题id",dataType="Long",dataTypeClass = Long.class,paramType = "query",required = true),
            @ApiImplicitParam(name="type",value="点赞对象类型，1表示话题，0表示评论",dataType="Integer",dataTypeClass = Integer.class,paramType = "query",required = true)
    })
    public Response praise(HttpServletRequest request,@RequestParam("id")Long id,@RequestParam("type")Integer type) throws Exception {
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        if(type==1){
            topicService.praise(id,userId);
        }else if(type==0){
            commentService.praise(id,userId);
        }else{
            return Response.clientError().code("A0300").message("参数值错误");
        }
        return Response.success().message("点赞成功");
    }

    @PostMapping("topic/unPraise")
    @ApiOperation(value="给话题或者评论取消点赞")
    public Response unPraise(HttpServletRequest request,@RequestParam("id")Long id,@RequestParam("type")Integer type) throws Exception {
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
        }
        if(type==1){
            topicService.unPraise(id,userId);
        }else if(type==0){
            commentService.unPraise(id,userId);
        }else{
            return Response.clientError().code("A0300").message("参数值错误");
        }
        return Response.success().message("取消点赞成功");
    }

    

}
