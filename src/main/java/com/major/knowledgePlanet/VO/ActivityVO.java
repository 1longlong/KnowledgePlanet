package
        com.major.knowledgePlanet.VO;

import com.major.knowledgePlanet.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO:此处写ActivityVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/5/1 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVO {
    private Activity activity;
    private Integer role;
}
