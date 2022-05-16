package
        com.major.knowledgePlanet.constValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO:此处写IntegralEnum类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/16 13:42
 */
@Getter
@AllArgsConstructor
public enum IntegralEnum {

    ADD_TOPIC_INTEGRAL(10),
    ADD_COMMENT_INTEGRAL(5),
    UPLOAD_RESOURCE_INTEGRAL(50),
    JOIN_COMPETITION_INTEGRAL(30);


    private Integer integral;
}
