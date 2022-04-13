package com.major.knowledgePlanet.service.impl;

import com.major.knowledgePlanet.entity.Notice;
import com.major.knowledgePlanet.mapper.NoticeMapper;
import com.major.knowledgePlanet.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cj
 * @date 2022/4/13 16:42
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Integer releaseNotice(Notice notice){return noticeMapper.releaseNotice(notice);}

    @Override
    public List<Notice> getAllNotice(){return  noticeMapper.getAllNotice();}
}
