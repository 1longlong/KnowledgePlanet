package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Notice;

import java.util.List;

public interface NoticeService {
  /**
   *发布公告接口
   *@author cj
   *@date 2022/4/20 16:32
   * @param notice
   * @return Integer
   */
    Integer releaseNotice(Notice notice);


   /**
    *获取系统公告接口，按时间倒序进行排序
    *@author cj
    *@date 2022/4/20 16:32

    * @return List<Notice>
    */
    List<Notice> getAllNotice();
}
