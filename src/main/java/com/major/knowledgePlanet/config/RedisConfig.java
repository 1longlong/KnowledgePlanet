package
com.major.knowledgePlanet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * TODO:此处写RedisConfig类的描述
 * @author 孟繁霖
 * @date 2022/4/9 19:57 
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String ,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化方式
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<Object>(Object.class);

        //键和hash键采用string序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);

        //值和hash值采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
}
