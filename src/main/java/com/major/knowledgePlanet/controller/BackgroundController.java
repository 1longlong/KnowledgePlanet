package com.major.knowledgePlanet.controller;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.jwt.JWTUtil;
import com.major.knowledgePlanet.entity.User;
import com.major.knowledgePlanet.result.Response;
import com.major.knowledgePlanet.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags="后台管理模块",value = "Background")
public class BackgroundController {
    @Value("${saltValue}")
    private String saltValue;

    @Resource(name="userInfoServiceImpl")
    private UserInfoService userInfoService;

    @PostMapping("background/updateNameById")
    @ApiOperation(value = "更新用户信息")
    public Response updateNameById(HttpServletRequest request , @RequestParam(value = "newName") String newName){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            if(userInfoService.countName(newName)==0){
                Integer result = userInfoService.updateNameByUserId(userId,newName);
                if(result!=0){
                    return Response.success().message("修改成功").data("result",result);
                }else{
                    return Response.serverError().message("修改失败").data("result",result);
                }
            }else{
                return Response.serverError().message("修改失败,用户名已存在");
            }


        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    @PostMapping("background/updateAvatarById")
    @ApiOperation("修改头像")
    public Response updateAvatarById(HttpServletRequest request , @RequestParam(value = "newAvatar") String newAvatar){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);

            Integer result = userInfoService.updateAvatarByUserId(userId, newAvatar);
            if (result != 0) {
                return Response.success().message("修改成功").data("result", result);
            } else {
                return Response.serverError().message("修改失败").data("result", result);
            }
        }
        return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    @GetMapping("background/getUserById")
    @ApiOperation("根据id获取用户信息")
    public Response getUserById(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token==null){
            return Response.clientError().code("B0201").message("未获取到token");
        } if(JWTUtil.verify(token, saltValue.getBytes())) {
            Long userId = ((Integer) JWTUtil.parseToken(token).getPayload("userId")).longValue();
            System.out.println("userId:" + userId);
            User result = userInfoService.getUserById(userId);
            if(result != null){
                return Response.success().message("查找成功").data("result",result);
            }else{
                return Response.serverError().message("查找失败");
            }
        }return Response.clientError().code("A0204").message("身份验证失败，请重新登录！");
    }

    @GetMapping("background/searchUser")
    @ApiOperation(value="搜索用户")
    public Response searchUser(@RequestParam("info")String info){
        List<User> users = userInfoService.searchUser(info);
        return Response.success().data("userList",users);
    }

    @PostMapping("background/changeUserStatus")
    @ApiOperation(value="改变用户状态")
    public Response changeUserStatus(@RequestParam("userId")Long userId,@RequestParam("status")Integer status){
        userInfoService.changeUserStatus(userId,status);
        return Response.success();
    }

    @PostMapping("background/adminLogin")
    @ApiOperation(value="管理员登录")
    public Response adminLogin(@RequestParam("adminId")String adminId,@RequestParam("password")String password){
        String realPassword = userInfoService.getAdminPassword(adminId);
        Digester sha256=new Digester(DigestAlgorithm.SHA256);
        String sha256Password = sha256.digestHex(password);
        System.out.println(sha256Password);
        if(realPassword==null || !realPassword.equals(sha256Password)){
            return Response.clientError().code("A0203").message("用户密码错误");
        }
        Map<String, Object> map = new HashMap<>(4){
            private static final long serialVersionUID = 1L;
            {
                put("adminId",adminId);
                put("expireTime", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);//1天
            }
        };
        String token = JWTUtil.createToken(map, saltValue.getBytes());
        return Response.success().data("token",token);
    }




}
