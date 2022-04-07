package
        com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写LoginLog类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/7 11:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private Long id;
    private Long u_id;
    private Date time;
    private String ip;
    private Integer status;
    private String browser;
}
