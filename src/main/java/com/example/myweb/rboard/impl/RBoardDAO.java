package com.example.myweb.rboard.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.example.myweb.common.JDBCUtil;
import com.example.myweb.rboard.RBoardVO;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
// JdbcTemplate 적용
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository("rboardDAO")
public class RBoardDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	final String SQL_GET = "select * from review_board where seq=?";
	final String SQL_LIST = "select * from review_board order by seq desc";
	final String SQL_INSERT = "insert into review_board(seq, title, writer, content) " +
						"values((select nvl(max(seq),0)+1 from review_board),?,?,?)";
	final String SQL_UPDATE = "update review_board set title=?, content=? where seq=?";
	final String SQL_DELETE = "delete from review_board where seq=?";
	
	// 등록
	public void insertBoard(RBoardVO vo) {
		jdbcTemplate.update(SQL_INSERT, vo.getTitle(), vo.getWriter(), vo.getContent());
	}
	
	// 수정
	public void updateBoard(RBoardVO vo) {
		jdbcTemplate.update(SQL_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());
	}
	
	// 삭제
	public void deleteBoard(RBoardVO vo) {
		jdbcTemplate.update(SQL_DELETE, vo.getSeq());				
	}
	
	class RBoardMapper implements RowMapper<RBoardVO> {
		@Override
		public RBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			RBoardVO rboard = new RBoardVO();
			rboard.setSeq(rs.getInt(1));
			rboard.setTitle(rs.getString(2));
			rboard.setWriter(rs.getString(3));
			rboard.setContent(rs.getString(4));
			rboard.setRegdate(rs.getString(5));
			rboard.setCnt(rs.getInt(6));
			return rboard;
		}
	}
	
	// 글 상세 조회
	public RBoardVO getRBoard(RBoardVO vo) {
		RBoardMapper rowMapper = new RBoardMapper();
		Object[] objArr = new Object[]{vo.getSeq()};
		RBoardVO rboard = jdbcTemplate.queryForObject(SQL_GET, objArr, rowMapper);
		return rboard;
	}
	
	// 글 목록 조회
	public List<RBoardVO> getRBoardList(RBoardVO vo) {
		System.out.println("===> getrBoardList() - RBoardDAO 기능 처리");
		RBoardMapper rowMapper = new RBoardMapper();
		List<RBoardVO> rboardList = jdbcTemplate.query(SQL_LIST, rowMapper);
		return rboardList;
	}
}
