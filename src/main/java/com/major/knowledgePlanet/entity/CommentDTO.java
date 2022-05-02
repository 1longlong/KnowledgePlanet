package
        com.major.knowledgePlanet.entity;

import com.major.knowledgePlanet.DTO.CommentLikeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO:此处写CommentVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/27 9:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Comment comment;
    private Boolean liked;
    private String userName;
    private String avatar;
    private List<Reply> replyList;
}
