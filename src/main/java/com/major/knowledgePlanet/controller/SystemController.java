package
        com.major.knowledgePlanet.controller;

import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.UserInfoService;
import com.major.knowledgePlanet.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
@RestController
public class SystemController {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Value("${saltValue}")
    private String saltValue;

    @Value("${user.defaultAvatar}")
    private String defaultAvatar;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("system/getVerificationCode/{email}")
    public Response getVerificationCode(@PathVariable("email") String email) {
        boolean isMatch = ReUtil.isMatch("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", email);
        if (!isMatch) {
            Response.clientError().code("A0103").message("邮箱格式错误");
        }
        String verificationCode = EmailUtil.getVerificationCode();
        redisTemplate.opsForValue().set(email, verificationCode, 60 * 10, TimeUnit.SECONDS);
        //调用hutool工具类
        MailUtil.send(email, "激活验证码", EmailUtil.getContents(email, verificationCode), true);
        //缓存验证码,2分钟
        //TODO 验证码不返回
        return Response.success().data("verificationCode", verificationCode);
    }

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


    @PostMapping("system/login")
    public Response login(@RequestParam("email")String email,@RequestParam("password")String password){
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

        return Response.success().message("登录成功").data("token",token);
    }
}



