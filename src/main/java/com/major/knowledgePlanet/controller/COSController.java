package
        com.major.knowledgePlanet.controller;


import com.major.knowledgePlanet.result.Response;
import com.tencent.cloud.CosStsClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

/**
 * COS服务
 *
 * @author 孟繁霖
 * @date 2022/4/17 11:39
 */
@RestController
@Api(tags="COS服务模块",value = "COSController")
public class COSController {

    @GetMapping("cos/getTempSecret")
    @ApiOperation(value = "获取临时访问密钥")
    public Response getTempSecret(){
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {
            //这里的 SecretId 和 SecretKey 代表了用于申请临时密钥的永久身份（主账号、子账号等），子账号需要具有操作存储桶的权限。
            // 替换为您的云 api 密钥 SecretId
            config.put("secretId", "AKIDrBH8j5B1cx3uVv50f4E8nCMGwMWR6VeZ");
            // 替换为您的云 api 密钥 SecretKey
            config.put("secretKey", "hAOM5YljRt63lDPM3UfaGgqlTw96oGSP");


            // 临时密钥有效时长，单位是秒，默认 1800 秒，目前主账号最长 2 小时（即 7200 秒），子账号最长 36 小时（即 129600）秒
            config.put("durationSeconds", 1800);

            // 换成您的 bucket
            config.put("bucket", "covenant-1308013334");
            // 换成 bucket 所在地区
            config.put("region", "ap-shanghai");

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径
            // 列举几种典型的前缀授权场景：
            // 1、允许访问所有对象："*"
            // 2、允许访问指定的对象："a/a1.txt", "b/b1.txt"
            // 3、允许访问指定前缀的对象："a*", "a/*", "b/*"
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config.put("allowPrefixes", new String[] {
                    "*",
            });

            // 密钥的权限列表。必须在这里指定本次临时密钥所需要的权限。
            // 简单上传、表单上传和分块上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    //单个文件删除
                    "name/cos:DeleteObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 分块上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);

            com.tencent.cloud.Response res = CosStsClient.getCredential(config);
            System.out.println(res.credentials.tmpSecretId);
            System.out.println(res.credentials.tmpSecretKey);
            System.out.println(res.credentials.sessionToken);
            return Response.success().data("tmpSecretId",res.credentials.tmpSecretId).data("tmpSecretKey",res.credentials.tmpSecretKey).data("sessionToken",res.credentials.sessionToken)
                    .data("startTime",System.currentTimeMillis()/1000).data("expiredTime",System.currentTimeMillis()/1000+31536000);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

}
