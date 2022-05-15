package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Message;
import com.major.knowledgePlanet.entity.Notice;

import java.util.List;

public interface NoticeService {
  /**
   *发布公告接口
   *@author cj
   *@date 2022/4/20 16:32
   * @param notice
   * @return Long
   */
    Long releaseNotice(Notice notice);


   /**
    *获取系统公告接口，按时间倒序进行排序
    *@author cj
    *@date 2022/4/20 16:32

    * @return List<Notice>
    */
    List<Notice> getAllNotice();

    /**
    * 获取所有消息通知
    * @param userId 1
    * @return : java.util.List<com.major.knowledgePlanet.entity.Message>
    * @author Covenant
    * @date 2022-04-26 14:51
    */
    List<Message> getMessageById(Long userId);

    /**
    * 设置消息状态
    * @param messageId 1
    * @param status 1表示已读，0表示未读
    * @author Covenant
    * @date 2022-04-27 14:06
    */
    void setMessageStatus(Long messageId,Integer status);

    void changeNoticeStatus(Long noticeId,Integer status);


}
