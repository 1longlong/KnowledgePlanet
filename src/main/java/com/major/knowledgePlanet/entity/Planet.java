package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写Planet类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/11 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    private Long planetCode;
    private String planetName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer maxNumber;
    private String planetDescription;
    private String planetAvatar;
    private Integer hot;
}
