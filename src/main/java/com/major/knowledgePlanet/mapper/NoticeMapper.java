package com.major.knowledgePlanet.mapper;

import com.major.knowledgePlanet.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    /**
     *发布公告
     *@author cj
     *@date 2022/4/20 16:44
     * @param notice
     * @return Integer
     */
    Integer releaseNotice(Notice notice);
    /**
     *获取所有公告，按照日期倒序排序
     *@author cj
     *@date 2022/4/20 16:44
     * @return List<Notice>
     */
    List<Notice> getAllNotice();

    void changeNoticeStatus(Long noticeId,Integer status);
}
