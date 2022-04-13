package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户参与和创建的星球
 *
 * @author 孟繁霖
 * @date 2022/4/12 17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPlanetDTO {
    private Long userId;
    private Planet planet;
    private Integer role;
    private Integer integral;

}
