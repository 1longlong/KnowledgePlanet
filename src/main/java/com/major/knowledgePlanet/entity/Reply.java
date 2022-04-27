package
        com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO:此处写Reply类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/27 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private User author;
    private User replyToUser;
    private Comment comment;
}
