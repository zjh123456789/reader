package com.riyeyuedu.dao;

import com.riyeyuedu.entity.NoticeEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author haojie tang
 * @date 2018/10/8 13:46
 */
@Repository
public class NoticeDao {
    public NoticeEntity addNotice(SqlSession sqlSession, NoticeEntity noticeEntity) {
        sqlSession.insert("notice.insertNotice", noticeEntity);
        return noticeEntity;
    }

    public List<NoticeEntity> getNoticeList(SqlSession sqlSession) {
        return sqlSession.selectList("notice.getNoticeList");
    }

    public List<NoticeEntity> getNoticeListL7(SqlSession sqlSession) {
        return sqlSession.selectList("notice.getNoticeListL7");
    }

    public  NoticeEntity getNoticeById(SqlSession sqlSession, int id) {
        return sqlSession.selectOne("notice.getNoticeById", id);
    }

    public boolean deleteNotice(SqlSession sqlSession, int id) {
        int deleteNum = sqlSession.delete("notice.deleteNotice", id);
        return deleteNum == 1;
    }

    public NoticeEntity updateNotice(SqlSession sqlSession, NoticeEntity noticeEntity) {
        sqlSession.update("notice.updateNotice", noticeEntity);
        return noticeEntity;
    }
}
