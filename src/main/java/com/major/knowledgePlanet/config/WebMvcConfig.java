package
        com.major.knowledgePlanet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO:此处写WebMvcConfig类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/19 23:37
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //可以被跨域的路径，”/**”表示无限制。
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                // 允许跨域访问的源,允许跨域的域名，可以用*表示允许任何域名使用
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                // 预检间隔时间
                .maxAge(168000);
    }
}
