package
        com.major.knowledgePlanet.VO;

import com.major.knowledgePlanet.entity.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写CompetitionVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/10 20:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionVO {
    private Competition competition;
    private Integer totalScore;
}
