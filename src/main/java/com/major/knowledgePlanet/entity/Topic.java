package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author cj
 * @date 2022/4/21 9:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private Long topicId;
    private Long planetCode;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer praiseCount;
    private Integer commentCount;
    private String content;
    private String picture;
}
