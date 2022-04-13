package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写UserPlanetRel类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/12 21:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanetRel {
    private Long userId;
    private Long planetCode;
    private Integer role;
    private Integer integral;
}
