package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写Activity类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/1 21:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private Long activityId;
    private Long planetCode;
    private String title;
    private String description;
    private String address;
    private Integer status;
    private String tag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer curNumber;
    private Integer maxNumber;
}
