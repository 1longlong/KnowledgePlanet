package
        com.major.knowledgePlanet.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.LoginLog;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.LoginLogService;
import com.major.knowledgePlanet.service.UserInfoService;
import com.major.knowledgePlanet.util.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO:系统基本使用模块
 * 包含登录、注册等基本功能
 *
 * @author 孟繁霖
 * @date 2022/4/5 23:18
 */
@Api(tags="登录注册模块",value="SystemController")
@RestController
public class SystemController {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Value("${saltValue}")
    private String saltValue;

    @Value("${user.defaultAvatar}")
    private String defaultAvatar;

    @Value("${server.IP}")
    private String cookieDomain;

    @Value("${server.servlet.context-path}")
    private String cookiePath;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LoginLogService loginLogService;

    @ApiOperation(value="向邮箱发送验证码")
    @GetMapping("system/getVerificationCode/{email}")
    public Response getVerificationCode(@PathVariable("email") String email) {
        boolean isMatch = ReUtil.isMatch("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", email);
        if (!isMatch) {
            Response.clientError().code("A0103").message("邮箱格式错误");
        }
        String verificationCode = EmailUtil.getVerificationCode();
        redisTemplate.opsForValue().set(email, verificationCode, 60 * 10, TimeUnit.SECONDS);
        //调用hutool工具类
        String result = MailUtil.send(email, "激活验证码", EmailUtil.getContents(email, verificationCode), true);
        System.out.println(result);
        //缓存验证码,2分钟
        //TODO 验证码不返回
        return Response.success().data("verificationCode", verificationCode);
    }

    @ApiOperation(value="注册账号")
    @PostMapping("system/register")
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
        if (verificationCode.equals((String) redisTemplate.opsForValue().get(email))) {
            if (userInfoService.addUser(newUser) > 0) {
                return Response.success().data("user", newUser).message("注册成功");
            }
            return Response.serverError().message("新用户注册失败");
        }
        return Response.clientError().code("A0104").message("邮件校验码匹配失败");
    }


    @ApiOperation(value="登录")
    @PostMapping("system/login")
    public Response login(HttpServletResponse httpServletResponse,HttpServletRequest request, @RequestParam("email")String email, @RequestParam("password")String password){
        User user = userInfoService.getUserByEmail(email);
        if(user==null) {
            return Response.clientError().code("A0201").message("用户不存在");
        }
        if(user.getStatus().equals(2)){
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
        if(ip==null||ip.isEmpty()||ip.equalsIgnoreCase("unknown")){
          ip="unknown";
        }
        LoginLog loginLog = new LoginLog(null, user.getU_id(), new Date(), ip, user.getStatus(), browser);
        if(loginLogService.addLoginLog(loginLog)<=0){
            System.out.println(user.toString()+"的日志未记录");
        }
        //生成token
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("u_id", user.getU_id());
                put("u_name",user.getU_name());
                put("status",user.getStatus());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 1);//1天
            }
        };
        String token = JWTUtil.createToken(map, saltValue.getBytes());
        Cookie cookie = new Cookie("token", token);
        cookie.setDomain(cookieDomain);
        cookie.setPath(cookiePath);
        httpServletResponse.addCookie(cookie);
        return Response.success().message("登录成功").data("token",token);
    }
}



