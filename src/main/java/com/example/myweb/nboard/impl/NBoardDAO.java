package com.example.myweb.nboard.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.example.myweb.common.JDBCUtil;
import com.example.myweb.nboard.NBoardVO;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
// JdbcTemplate 적용
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository("nboardDAO")
public class NBoardDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	final String SQL_GET = "select * from noti_board where seq=?";
	final String SQL_LIST = "select * from noti_board order by seq desc";
	final String SQL_INSERT = "insert into noti_board(seq, title, writer, content) " +
						"values((select nvl(max(seq),0)+1 from noti_board),?,?,?)";
	final String SQL_UPDATE = "update noti_board set title=?, content=? where seq=?";
	final String SQL_DELETE = "delete from noti_board where seq=?";
	
	// 등록
	public void insertBoard(NBoardVO vo) {
		jdbcTemplate.update(SQL_INSERT, vo.getTitle(), vo.getWriter(), vo.getContent());
	}
	
	// 수정
	public void updateBoard(NBoardVO vo) {
		jdbcTemplate.update(SQL_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());
	}
	
	// 삭제
	public void deleteBoard(NBoardVO vo) {
		jdbcTemplate.update(SQL_DELETE, vo.getSeq());				
	}
	
	class NBoardMapper implements RowMapper<NBoardVO> {
		@Override
		public NBoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			NBoardVO nboard = new NBoardVO();
			nboard.setSeq(rs.getInt(1));
			nboard.setTitle(rs.getString(2));
			nboard.setWriter(rs.getString(3));
			nboard.setContent(rs.getString(4));
			nboard.setRegdate(rs.getString(5));
			nboard.setCnt(rs.getInt(6));
			return nboard;
		}
	}
	
	// 글 상세 조회
	public NBoardVO getNBoard(NBoardVO vo) {
		NBoardMapper rowMapper = new NBoardMapper();
		Object[] objArr = new Object[]{vo.getSeq()};
		NBoardVO nboard = jdbcTemplate.queryForObject(SQL_GET, objArr, rowMapper);
		return nboard;
	}
	
	// 글 목록 조회
	public List<NBoardVO> getNBoardList(NBoardVO vo) {
		NBoardMapper rowMapper = new NBoardMapper();
		List<NBoardVO> nboardList = jdbcTemplate.query(SQL_LIST, rowMapper);
		return nboardList;
	}
}
