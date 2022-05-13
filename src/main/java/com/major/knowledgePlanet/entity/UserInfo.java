package
        com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写UserInfo类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/12 23:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Long userId;
    private String userName;
    private String avatar;
}
