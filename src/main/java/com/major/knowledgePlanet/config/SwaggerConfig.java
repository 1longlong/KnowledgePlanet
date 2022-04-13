package
        com.major.knowledgePlanet.config;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * TODO:此处写SwaggerConfiguration类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/7 15:35
 */
@EnableOpenApi
@Configuration
@Data
public class SwaggerConfig {

    /**
    * 是否开启swagger，生产环境一般关闭
    */
    @Value("${swagger.enable}")
    private Boolean enable;
    /**
    * 项目应用名
    */
    @Value("${swagger.applicationName}")
    private String applicationName;
    /**
    * 项目版本信息
    */
    @Value("${swagger.applicationVersion}")
    private String applicationVersion;
    /**
    * 项目描述信息
    */
    @Value("${swagger.applicationDescription}")
    private String applicationDescription;

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(applicationName)
                .description(applicationDescription)
                .contact(new Contact("Covenant",null,"3324608778@qq.com"))
                .version(applicationVersion)
                .build();
    }

}
