package
        com.major.knowledgePlanet.controller;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.UserInfoService;
import io.swagger.annotations.Api;
import lombok.Setter;
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


    @PostMapping("userInfo/updatePassword")
    public Response updatePassword(HttpServletRequest request, @RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword") String newPassword) {
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        }
        if(JWTUtil.verify(token, saltValue.getBytes())){
            Long userId=((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:"+userId);
            User user= userInfoService.getUserById(userId);
            Digester sha256=new Digester(DigestAlgorithm.SHA256);
            if(user!=null&&sha256.digestHex(oldPassword).equals(user.getPassword())){
                if(userInfoService.updatePassword(userId,sha256.digestHex(newPassword))>0) {
                    return Response.success().message("更新成功");
                }
                return Response.serverError().code("B0001").message("更新失败");
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

}
