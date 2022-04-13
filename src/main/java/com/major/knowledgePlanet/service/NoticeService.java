package com.major.knowledgePlanet.service;

import com.major.knowledgePlanet.entity.Notice;

import java.util.List;

public interface NoticeService {

    Integer releaseNotice(Notice notice);

    List<Notice> getAllNotice();
}
