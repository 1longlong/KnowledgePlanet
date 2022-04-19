package
        com.major.knowledgePlanet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

/**
 * 用户资源类
 * 包含资源和用户之间关系
 *
 * @author 孟繁霖
 * @date 2022/4/15 22:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVO {

    private Long resourceId;
    private Long uploaderId;
    private Long planetCode;
    private String resourceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;
    private String link;
    private String coverage;
    private String resourceDescription;
    private Integer status;
    private String checkInfo;
    private Boolean isRecommended;
    private Integer likeCount;
    private Integer collectCount;
    private String details;


    private Boolean liked;
    private Boolean collected;

    private String uploaderName;
    private String uploaderAvatar;

    /**
    * 资源标签列表
    */
    private ArrayList<String> tagList=new ArrayList<>();

}
