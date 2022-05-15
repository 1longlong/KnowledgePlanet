package
        com.major.knowledgePlanet.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.constValue.SystemConst;
import com.major.knowledgePlanet.constValue.UserStatusEnum;
import com.major.knowledgePlanet.entity.*;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.LoginLogService;
import com.major.knowledgePlanet.service.NoticeService;
import com.major.knowledgePlanet.service.TopicService;
import com.major.knowledgePlanet.service.UserInfoService;
import com.major.knowledgePlanet.util.EmailUtil;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 系统基本使用模块
 * 包含登录、注册等基本功能
 *
 * @author 孟繁霖
 * @date 2022/4/5 23:18
 */
@Api(tags="登录注册模块",value="SystemController")
@RestController
public class SystemController {

    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${saltValue}")
    private String saltValue;

    @Value("${defaultAvatar}")
    private String defaultAvatar;

    @Resource(name="userInfoServiceImpl")
    private UserInfoService userInfoService;

    @Resource(name="loginLogServiceImpl")
    private LoginLogService loginLogService;

    @Resource(name="noticeServiceImpl")
    private NoticeService noticeService;

    @Resource(name="topicServiceImpl")
    private TopicService topicService;

    @GetMapping("system/getVerificationCode/{email}")
    @ApiOperation(value="向邮箱发送验证码")
    @ApiImplicitParams(@ApiImplicitParam(name="email",value="邮箱",dataType="String",dataTypeClass =String.class, paramType = "path",required = true))
    public Response getVerificationCode(@PathVariable("email") String email) {
        boolean isMatch = ReUtil.isMatch("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", email);
        if (!isMatch) {
            Response.clientError().code("A0103").message("邮箱格式错误");
        }
        String verificationCode = EmailUtil.getVerificationCode();
        redisTemplate.opsForValue().set(email, verificationCode, 60 * 10, TimeUnit.SECONDS);
        //调用HuTool工具类
        String result = MailUtil.send(email, "激活验证码", EmailUtil.getContents(email, verificationCode), true);
        System.out.println(result);
        //缓存验证码,2分钟
        //TODO 验证码不返回
        return Response.success().data("verificationCode", verificationCode);
    }

    @ApiOperation(value="注册账号")
    @PostMapping("system/register")
    @ApiImplicitParams({@ApiImplicitParam(name="nickName",value="昵称",dataType = "String",dataTypeClass =String.class,paramType = "query",required = true),
    @ApiImplicitParam(name="email",value="邮箱",dataType = "String",dataTypeClass =String.class,paramType = "query",required = true),
    @ApiImplicitParam(name="verificationCode",value="验证码",dataType = "String",dataTypeClass =String.class,paramType = "query",required = true),
    @ApiImplicitParam(name="password",value="密码",dataType = "String",dataTypeClass =String.class,paramType = "query",required = true)})
    public Response register(@RequestParam(value = "nickName") String nickName, @RequestParam("email") String email,
                             @RequestParam("verificationCode") String verificationCode, @RequestParam("password") String password) {
        User user = userInfoService.getUserByEmail(email);
        if (user != null) {
            return Response.clientError().code("A0101").message("该邮箱已注册");
        }
        //密码sha256加密
        Digester sha256=new Digester(DigestAlgorithm.SHA256);
        String sha256Password = sha256.digestHex(password);
        User newUser = new User(null, nickName, new Date(), new Date(), 1, defaultAvatar, email, sha256Password);
        if (verificationCode.equals(redisTemplate.opsForValue().get(email))) {
            if (userInfoService.addUser(newUser) > 0) {
                return Response.success().data("user", newUser).message("注册成功");
            }
            return Response.serverError().message("新用户注册失败");
        }
        return Response.clientError().code("A0104").message("邮件校验码匹配失败");
    }



    @ApiOperation(value="登录")
    @PostMapping("system/login")
    @ApiImplicitParams({@ApiImplicitParam(name="email",value="邮箱",dataType = "String",dataTypeClass =String.class,paramType = "query"),
        @ApiImplicitParam(name="password",value="密码",dataType = "String",dataTypeClass =String.class,paramType = "query")})
    public Response login(HttpServletRequest request, @RequestParam("email")String email, @RequestParam("password")String password){
        User user = userInfoService.getUserByEmail(email);
        if(user==null) {
            return Response.clientError().code("A0201").message("用户不存在");
        }
        if(user.getStatus().equals(UserStatusEnum.DISABLE.getStatus())){
            return Response.clientError().code("A0202").message("用户已被冻结");
        }
        Digester sha256=new Digester(DigestAlgorithm.SHA256);
        String sha256Password = sha256.digestHex(password);
        if(!user.getPassword().equals(sha256Password)){
            return Response.clientError().code("A0203").message("用户密码错误");
        }
        //密码正确，记录登录日志
        String userAgentStr = request.getHeader("user-agent");
        UserAgent ua = UserAgentUtil.parse(userAgentStr);
        String browser=ua.getBrowser().toString();
        String ip=request.getHeader("X-Forwarded-For");

        //TODO:待获取ip
        if(ip==null||ip.isEmpty()|| SystemConst.NO_IP.equalsIgnoreCase(ip)){
          ip=SystemConst.NO_IP;
        }
        LoginLog loginLog = new LoginLog(null, user.getUserId(), new Date(), ip, browser);
        if(loginLogService.addLoginLog(loginLog)<=0){
            System.out.println(user.toString()+"的日志未记录");
        }
        //生成token
        Map<String, Object> map = new HashMap<>(4){
            private static final long serialVersionUID = 1L;
            {
                put("userId", user.getUserId());
                put("userName",user.getUserName());
                put("status",user.getStatus());
                put("expireTime", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);//1天
            }
        };
        String token = JWTUtil.createToken(map, saltValue.getBytes());
        return Response.success().message("登录成功").data("token",token).data("user",user);
    }

    @GetMapping("system/getLoginLog")
    @ApiOperation(value="根据用户id获取登录日志")
    public Response getLoginLog(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);

            List<LoginLog> loginLogList = loginLogService.getLoginLogById(userId);
            if (!loginLogList.isEmpty()) {
                return Response.success().message("查找成功").data("result", loginLogList);
            } else {
                return Response.serverError().message("未查询到相关结果").data("result", loginLogList);
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }


    @PostMapping("system/releaseNotice")
    @ApiOperation(value="发布系统公告")
    public Response releaseNotice(@RequestBody Notice notice){
        Long noticeId= noticeService.releaseNotice(notice);
        return  Response.success().data("noticeId",noticeId);
    }

    @GetMapping("system/getAllNotice")
    @ApiOperation(value="查找现有的公告")
    public Response getAllNotice(){
        List<Notice> notices = noticeService.getAllNotice();
        if(!notices.isEmpty()){
            return Response.success().message("查找成功").data("notices" ,notices);
        }else{
            return Response.serverError().message("未查到公告");
        }
    }
    @GetMapping("system/getLoginRecord")
    @ApiOperation(value="获取用户登录时间记录")
    public Response getLoginRecord(HttpServletRequest request) {
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        System.out.println("userId:" + userId);
        List<String> result = loginLogService.getActiveCalender(userId);
        if(result!=null) {
            return Response.success().message("查找成功").data("date", result);
        }else{
            return  Response.serverError().message("未查到相关记录");
        }
    }

    @GetMapping("system/getMessage")
    @ApiOperation(value="获取所有通知消息")
    public Response getMessage(HttpServletRequest request){
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        System.out.println("userId:" + userId);
        List<Message> messageList = noticeService.getMessageById(userId);
        return Response.success().data("messageList",messageList);
    }


    @PostMapping("system/setMessageStatus")
    @ApiOperation(value="设置消息状态，1表示已读，0表示未读")
    @ApiImplicitParams({
            @ApiImplicitParam(name="messageId",value="消息id",dataType="Long",dataTypeClass = Long.class,paramType="query",required = true),
            @ApiImplicitParam(name="status",value="消息状态",dataType = "Integer",dataTypeClass = Integer.class,paramType = "query",required = true)
    })
    public Response setMessageStatus(@RequestParam("messageId")Long messageId,@RequestParam("status")Integer status){
        noticeService.setMessageStatus(messageId,status);
        return Response.success();
    }


    @PostMapping("system/changeNoticeStatus")
    @ApiOperation(value="开放公告或者关闭公告")
    public Response changeNoticeStatus(@RequestParam("noticeId")Long noticeId,@RequestParam("status")Integer status){
        noticeService.changeNoticeStatus(noticeId,status);
        return Response.success();
    }


}



