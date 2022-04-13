package
        com.major.knowledgePlanet.util;

import cn.hutool.jwt.JWTUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO:此处写TokenParseUtil类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/12 22:07
 */
public class TokenParseUtil {
    /**
    * 解析请求中的token获得用户id
    * @param httpServletRequest 1
    * @param saltValue 盐值
    * @return : java.lang.Long 用户id，认证失败返回null
    * @author Covenant
    * @date 2022-04-12 22:21
    */
    public static Long getUserId(HttpServletRequest httpServletRequest,String saltValue){
        String token = httpServletRequest.getHeader("token");
        if(JWTUtil.verify(token,saltValue.getBytes())){
            return ((Integer)JWTUtil.parseToken(token).getPayload("userId")).longValue();
        }
        return null;
    }
}
