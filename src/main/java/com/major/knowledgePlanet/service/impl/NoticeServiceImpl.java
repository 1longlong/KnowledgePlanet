package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Message;
import com.major.knowledgePlanet.entity.Notice;
import com.major.knowledgePlanet.mapper.MessageMapper;
import com.major.knowledgePlanet.mapper.NoticeMapper;
import com.major.knowledgePlanet.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author cj
 * @date 2022/4/13 16:42
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Long releaseNotice(Notice notice){
        notice.setCreateTime(new Date());
        notice.setStatus(1);
        noticeMapper.releaseNotice(notice);
        return notice.getNoticeId();
    }

    @Override
    public List<Notice> getAllNotice(){return  noticeMapper.getAllNotice();}

    @Override
    public List<Message> getMessageById(Long userId) {
        return messageMapper.getMessageById(userId);
    }

    @Override
    public void setMessageStatus(Long messageId, Integer status) {
         messageMapper.setMessageStatus(messageId,status);
    }

    @Override
    public void changeNoticeStatus(Long noticeId, Integer status) {
        noticeMapper.changeNoticeStatus(noticeId,status);
    }
}
