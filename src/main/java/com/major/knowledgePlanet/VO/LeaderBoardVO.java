package
        com.major.knowledgePlanet.VO;

import com.major.knowledgePlanet.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写LeaderBoardVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/12 23:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardVO {
    private UserInfo userInfo;
    private Integer count;
}
