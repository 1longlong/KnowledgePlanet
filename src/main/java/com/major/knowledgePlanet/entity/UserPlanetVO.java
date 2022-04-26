package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写UserPlanetVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/21 21:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanetVO {
    private Planet planet;
    private Integer integral;
}
