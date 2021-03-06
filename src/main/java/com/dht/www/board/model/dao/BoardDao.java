package com.dht.www.board.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dht.www.board.model.vo.Board;
import com.dht.www.user.model.vo.Users;

import common.util.Paging;

@Repository
public class BoardDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 게시글 목록 조회
	public List<Map<String, String>> selectBoardList(Paging page) {
		return sqlSession.selectList("BOARD.selectBoardList", page);
	}
	
	// 게시글 개수 확인
	public int selectContentCnt() {
		return sqlSession.selectOne("BOARD.selectContentCnt");
	}
	
	// 게시글 상세 조회
	public Map<String, String> selectBoard(int no) {
		return sqlSession.selectOne("BOARD.selectBoard", no);
	}

	// 게시글 조회수 +1
	public void updateBoardCount(int no) {
		sqlSession.update("BOARD.updateBoardCount", no);
	}
	
	// 게시글 작성
	public int insertBoard(Board board) {
		return sqlSession.insert("BOARD.insertBoard", board);
	}

	// 게시글 수정
	public int updateBoardContent(Board board) {
		return sqlSession.insert("BOARD.updateBoardContent", board);
	}
	
	public Map<String, Object> selectProfile(Users login) {
		return sqlSession.selectOne("BOARD.selectProfile", login);
	}

}
