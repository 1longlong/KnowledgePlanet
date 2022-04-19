package
        com.major.knowledgePlanet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * TODO:此处写UploadResourceVO类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/19 20:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResourceVO implements Serializable {
    private Long planetCode;
    private String resourceName;
    private String link;
    private String coverage;
    private String resourceDescription;
    private String details;

    private ArrayList<String> tagList;

}
