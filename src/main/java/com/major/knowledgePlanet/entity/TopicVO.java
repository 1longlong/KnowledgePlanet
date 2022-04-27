package
        com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写TopicVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/26 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicVO {
    private String userName;
    private String avatar;
    private Topic topic;
}
