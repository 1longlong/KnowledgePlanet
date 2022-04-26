package
com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO:此处写ResourceCheckVO类的描述
 * @author 孟繁霖
 * @date 2022/4/22 16:10 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCheckVO {
    private Long resourceId;
    private String resourceName;
    private Integer checkResult;
    private String checkInfo;
    private Long uploaderId;
}
