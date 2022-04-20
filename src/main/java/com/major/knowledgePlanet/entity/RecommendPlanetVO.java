package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写RecommendPlanetVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/20 15:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPlanetVO {

   private Planet planet;


    private String uploaderName;
    private String uploaderAvatar;
}
