package
        com.major.knowledgePlanet.service;

/**
 * TODO:此处写RedisService类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/15 21:55
 */
public interface RedisService {

    /**
     * 将缓存中的所有资源点赞数持久化到数据库
     * @author Covenant
     * @date 2022-04-15 20:23
     */
    void persistResourceLikeCount();

    /**
    * 将缓存中的所有资源收藏数持久化到数据库
    * @author Covenant
    * @date 2022-04-16 18:02
    */
    void persistResourceCollectCount();

    /**
    * 将缓存中所有资源-用户-收藏关系持久化到数据库
    * @author Covenant
    * @date 2022-04-16 18:02
    */
    void persistResourceUserIsCollect();


    /**
    * 将缓存中所有资源-用户-点赞关系持久化到数据库
    * @author Covenant
    * @date 2022-04-15 23:00
    */
    void persistResourceUserIsLike();
}
