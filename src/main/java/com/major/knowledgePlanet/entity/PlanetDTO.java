package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * TODO:此处写PlanetDTO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/12 17:53
 */
public class PlanetDTO {
    private String planetCode;
    private String planetName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer maxNumber;
    private String planetDescription;
    private String planetAvatar;
    private Integer hot;
}
