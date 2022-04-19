package
        com.major.knowledgePlanet.controller;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.UserInfoService;
import com.major.knowledgePlanet.util.RedisUtil;
import com.major.knowledgePlanet.util.TokenParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO:此处写UserInfoController类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/10 19:26
 */
@Api(tags="用户信息模块",value="UserInfoController")
@RestController
@Setter
public class UserInfoController {
    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="userInfoServiceImpl")
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value="更新密码")
    @PostMapping("userInfo/updatePassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oldPassword",value="旧密码",dataType="String",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="newPassword",value="新密码",dataType="String",dataTypeClass = String.class,paramType = "query",required = true)
    })
    public Response updatePassword(HttpServletRequest request, @RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword") String newPassword) {
        Long userId= TokenParseUtil.getUserId(request,saltValue);
        if(userId==null){
            return Response.clientError().code("B0204").message("身份验证失败");
        }
        System.out.println("userId:"+userId);
        User user= userInfoService.getUserById(userId);
        Digester sha256=new Digester(DigestAlgorithm.SHA256);
        if(user!=null&&sha256.digestHex(oldPassword).equals(user.getPassword())){
            if(userInfoService.updatePassword(userId,sha256.digestHex(newPassword))>0) {
                return Response.success().message("更新成功");
            }
            return Response.serverError().code("B0001").message("更新失败");
        }
        return Response.clientError().code("B0204").message("密码错误");
    }

    @ApiOperation(value="设置新密码")
    @PostMapping("userInfo/setNewPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name="newPassword",value="新密码",dataType="String",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="email",value="邮箱",dataType="String",dataTypeClass = String.class,paramType = "query",required = true),
            @ApiImplicitParam(name="verificationCode",value = "验证码",dataType="String",dataTypeClass = String.class,paramType = "query",required = true)
    })
    public Response setNewPassword(@RequestParam("newPassword")String newPassword,@RequestParam("email")String email,@RequestParam("verificationCode")String verificationCode){
        String redisVerificationCode = (String)redisUtil.get(email);
        if(redisVerificationCode==null||!redisVerificationCode.equals(verificationCode)){
            return Response.clientError().code("A0104").message("邮件校验码匹配失败");
        }
        Digester sha256=new Digester(DigestAlgorithm.SHA256);
        userInfoService.updatePassword(email,sha256.digestHex(newPassword));
        return Response.success().message("新密码设置成功");

    }

}
