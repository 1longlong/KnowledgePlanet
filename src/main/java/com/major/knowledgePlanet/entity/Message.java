package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写Message类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/20 18:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long messageId;
    private Long from;
    private Long to;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer status;
}
