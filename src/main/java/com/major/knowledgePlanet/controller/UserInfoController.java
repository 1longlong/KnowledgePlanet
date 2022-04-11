package
        com.major.knowledgePlanet.controller;

import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO:此处写UserInfoController类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/10 19:26
 */
@RestController
public class UserInfoController {
    @Value("${saltValue}")
    private String saltValue;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("userInfo/updatePassword")
    public Response updatePassword(HttpServletRequest request, @RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword") String newPassword) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return Response.clientError().code("B0201").message("未获取到cookie");
        }
        String token="";
        for (Cookie cookie : cookies) {
            if(cookie!=null&&"token".equals(cookie.getName())){
                token=cookie.getValue();
            }
        }
        if(JWTUtil.verify(token, saltValue.getBytes())){
            Long u_id=(Long)JWTUtil.parseToken(token).getPayload("u_id");
            User user= userInfoService.getUserById(u_id);
            if(user!=null&&oldPassword.equals(user.getPassword())){
                if(userInfoService.updatePassword(u_id,newPassword)>0) {
                    return Response.success().message("更新成功");
                }
                return Response.serverError().code("B0001").message("更新失败");
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    @GetMapping("userInfo/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
