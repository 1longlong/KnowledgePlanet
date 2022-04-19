package
        com.major.knowledgePlanet.config;

import com.major.knowledgePlanet.service.RedisService;
import com.major.knowledgePlanet.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * TODO:此处写ScheduleConfig类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/15 17:34
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    private RedisUtil redisUtil;

    @Resource(name="redisServiceImpl")
    private RedisService redisService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void task(){

        redisService.persistResourceLikeCount();
        redisService.persistResourceCollectCount();
        redisService.persistResourceUserIsLike();
        redisService.persistResourceUserIsCollect();
    }

}
