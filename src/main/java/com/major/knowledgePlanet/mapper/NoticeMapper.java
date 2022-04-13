package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    Integer releaseNotice(Notice notice);

    List<Notice> getAllNotice();
}
