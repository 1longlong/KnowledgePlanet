package
        com.major.knowledgePlanet.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {
    private boolean success;
    private String code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private Response() {

    }

    public static Response getResponse(ResponseCodeEnum responseCodeEnum) {
        Response response = new Response();
        response.setCode(responseCodeEnum.getCode());
        response.setSuccess(responseCodeEnum.getSuccess());
        response.setMessage(responseCodeEnum.getMessage());
        return response;
    }


    public static Response success() {
        return getResponse(ResponseCodeEnum.SUCCESS);
    }

    public static Response clientError(){
        return getResponse(ResponseCodeEnum.CLIENT_ERROR);
    }

    public static Response serverError(){
        return getResponse(ResponseCodeEnum.SERVER_ERROR);
    }


    public Response data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public Response data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Response message(String message) {
        this.setMessage(message);
        return this;
    }

    public Response code(String code) {
        this.setCode(code);
        return this;
    }

    public Response success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}

