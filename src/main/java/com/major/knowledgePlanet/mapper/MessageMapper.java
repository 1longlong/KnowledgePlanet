package
        com.major.knowledgePlanet.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.major.knowledgePlanet.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * TODO:此处写MessageMapper类的描述
 *
 * @author 孟繁霖
 * @date 2022/4/20 18:00
 */
@Mapper
@Repository
public interface MessageMapper {

    void addMessage(Message message);
}
