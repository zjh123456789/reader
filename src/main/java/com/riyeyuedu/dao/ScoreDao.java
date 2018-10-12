package com.riyeyuedu.dao;

import com.riyeyuedu.entity.ScoreEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ScoreDao {
    public ScoreEntity addScore(SqlSession sqlSession, ScoreEntity scoreEntity) {
        sqlSession.insert("score.insertScore", scoreEntity);
        return scoreEntity;
    }

    public ScoreEntity updateScore(SqlSession sqlSession, ScoreEntity scoreEntity) {
        sqlSession.update("score.updateScore", scoreEntity);
        return scoreEntity;
    }

    public ScoreEntity getScore(SqlSession sqlSession, ScoreEntity scoreEntity) {
        return sqlSession.selectOne("score.getScore", scoreEntity);
    }

    public Integer getScoreByUid(SqlSession sqlSession, ScoreEntity scoreEntity) {
        return sqlSession.selectOne("score.getScoreByUid", scoreEntity);
    }

    public List<Map<String, Object>> getScoresByUid(SqlSession sqlSession, int uid) {
        return sqlSession.selectList("score.getScoresByUid", uid);
    }

    public List<Map<String, Object>> getScoreByNid(SqlSession sqlSession, Long nid) {
        return sqlSession.selectList("score.getScoreByNid", nid);
    }

    public Integer getScoreNumByNid(SqlSession sqlSession, Long nid) {
        return sqlSession.selectOne("score.getScoreNumByNid", nid);
    }

    public Double getAvgScoreByNid(SqlSession sqlSession, Long nid) {
        return sqlSession.selectOne("score.getAvgScoreByNid", nid);
    }

    public boolean deleteScore(SqlSession sqlSession, Long nid) {
        int deleteNum = sqlSession.delete("score.deleteScore", nid);
        return deleteNum == 1;
    }
}
