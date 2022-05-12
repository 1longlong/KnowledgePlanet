package
        com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写Question类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/10 20:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Long questionId;
    private Long competitionId;
    private String content;
    private String answer;
    private Integer score;
    private String items;
}
