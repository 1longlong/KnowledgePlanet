package
        com.major.knowledgePlanet.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写CommentLikeDTO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/29 15:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDTO {
    private Long userId;
    private Long commentId;
    private Long topicId;
    private Long parentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer praiseCount;
    private String content;
    private Boolean liked;
    private Long firstCommentId;
}
