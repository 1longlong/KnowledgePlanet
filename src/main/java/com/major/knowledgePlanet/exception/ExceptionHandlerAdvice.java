package com.major.knowledgePlanet.exception;

import com.major.knowledgePlanet.result.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.nio.file.AccessDeniedException;

/**
* 统一异常处理类
*
* @author Covenant
* @date 2022-04-10 17:30
*/
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
    * 请求参数格式错误
    * @return : com.major.knowledgePlanet.result.Response
    * @author Covenant
    * @date 2022-04-10 17:22
    */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response badRequestException(IllegalArgumentException exception) {
        exception.printStackTrace();
        return Response.clientError().code("A0303").message("请求参数格式错误！");
    }

    /**
    * 用户无权限访问
    * @return : com.major.knowledgePlanet.result.Response
    * @author Covenant
    * @date 2022-04-10 17:22
    */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response badRequestException(AccessDeniedException exception) {
        exception.printStackTrace();
        return Response.clientError().code("A0205").message("当前用户无权限访问！");
    }

    /**
    * 参数缺失异常处理
    * @return : null
    * @author Covenant
    * @date 2022-04-10 17:21
    */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response badRequestException(Exception exception) {
        exception.printStackTrace();
        return Response.clientError().code("A0302").message("缺少请求参数！");
    }

    /**
    * 空指针异常
    * @return : com.major.knowledgePlanet.result.Response
    * @author Covenant
    * @date 2022-04-10 17:26
    */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleTypeMismatchException(NullPointerException exception) {
        exception.printStackTrace();
        return  Response.serverError().code("B0201").message("空指针异常！");
    }


    /**
    * 未知系统异常
    * @return : com.major.knowledgePlanet.result.Response
    * @author Covenant
    * @date 2022-04-10 17:29
    */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response exception(Throwable throwable) {
        throwable.printStackTrace();
        return Response.serverError().message("系统异常，请联系管理员!");
    }



}
