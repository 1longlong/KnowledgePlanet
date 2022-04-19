package
        com.major.knowledgePlanet.constValue;

/**
 * TODO:此处写RedisKey类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/15 19:02
 */
public class RedisKey {
    /**
    * 资源点赞数hash表键值
    */
    public static final String RESOURCE_LIKE_COUNT="resourceLikeCount";

    /**
    * 资源收藏数hash表键值
    */
    public static final String RESOURCE_COLLECT_COUNT="resourceCollectCount";
    /**
    * 资源-用户-是否点赞键值
    */
    public static final String RESOURCE_USER_ISLIKE="resourceLikeUser";

    /**
     * 资源-用户-是否收藏键值
     */
    public static final String RESOURCE_USER_ISCOLLECT="resourceCollectUser";

}
