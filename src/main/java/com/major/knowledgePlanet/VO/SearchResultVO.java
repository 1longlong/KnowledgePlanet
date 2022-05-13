package
        com.major.knowledgePlanet.VO;

import com.major.knowledgePlanet.entity.Planet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写SearchResultVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/13 11:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultVO {
    private Integer role;
    private Planet planet;
}
