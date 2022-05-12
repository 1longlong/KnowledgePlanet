package
        com.major.knowledgePlanet.VO;

import com.major.knowledgePlanet.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写QuestionVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/12 21:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {
    private Question question;
    private String userAnswer;
    private Integer getScore;
    private Boolean right;
}
