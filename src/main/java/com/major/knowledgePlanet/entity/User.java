package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO:此处写User类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/5 23:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long u_id;
    private String u_name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date update_time;
    private Integer status;
    private String avatar;
    private String email;
    private String password;
}
