package com.riyeyuedu.service;

import com.riyeyuedu.dao.NoticeDao;
import com.riyeyuedu.entity.NoticeEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author haojie tang
 * @date 2018/10/8 13:52
 */
@Service
public class NoticeService {
    private SqlSession sqlSession;

    private NoticeDao noticeDao;

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setNoticeDao(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    public NoticeEntity addNotice(NoticeEntity noticeEntity) {
        return noticeDao.addNotice(sqlSession, noticeEntity);
    }

    public List<NoticeEntity> getNoticeList() {
        return noticeDao.getNoticeList(sqlSession);
    }

    public List<NoticeEntity> getNoticeListL7() {
        return noticeDao.getNoticeListL7(sqlSession);
    }

    public NoticeEntity getNoticeById(int id) {
        return noticeDao.getNoticeById(sqlSession, id);
    }

    public boolean deleteNotice(int id) {
        return noticeDao.deleteNotice(sqlSession, id);
    }

    public NoticeEntity updateNotice(NoticeEntity noticeEntity) {
        return noticeDao.updateNotice(sqlSession, noticeEntity);
    }
}
