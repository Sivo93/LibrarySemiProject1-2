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

// @Repository("boardDAO")
public class RBoardDAOJdbc {
	@Resource(name="jdbcUtil")
	private JDBCUtil jdbcUtil;
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	final String SQL_GET = "select * from review_board where seq=?";
	final String SQL_LIST = "select * from review_board order by seq desc";
	final String SQL_INSERT = "insert into review_board(seq, title, writer, content) " +
						"values((select nvl(max(seq),0)+1 from review_board),?,?,?)";
	final String SQL_UPDATE = "update review_board set title=?, content=? where seq=?";
	final String SQL_DELETE = "delete from review_board where seq=?";
	
	// 등록
	public void insertRBoard(RBoardVO vo) {
		
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			int res = pstmt.executeUpdate();
			if(res>0) {
				conn.commit();
				System.out.println("입력 성공!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(null, pstmt, conn);
		}
	}
	
	// 글수정
	public void updateRBoard(RBoardVO vo) {
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getSeq());
			int res = pstmt.executeUpdate();
			if(res>0) {
				conn.commit();
				System.out.println("수정 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(null, pstmt, conn);
		}
	}
	
	// 글삭제
	public void deleteRBoard(RBoardVO vo) {
		
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_DELETE);
			pstmt.setInt(1, vo.getSeq());
			int res = pstmt.executeUpdate();
			if(res>0) {
				conn.commit();
				System.out.println("데이터 삭제 성공!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(null, pstmt, conn);
		}
		
	}
	
	// 글 상세 조회
	public RBoardVO getRBoard(RBoardVO vo) {
		RBoardVO rboard = null;
		
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_GET);
			pstmt.setInt(1, vo.getSeq());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				rboard = new RBoardVO();
				rboard.setSeq(rs.getInt(1));
				rboard.setTitle(rs.getString(2));
				rboard.setWriter(rs.getString(3));
				rboard.setContent(rs.getString(4));
				rboard.setRegdate(rs.getString(5));
				rboard.setCnt(rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(rs, pstmt, conn);
		}
		
		return rboard;
	}
	
	// 글 목록 조회
	public List<RBoardVO> getRBoardList(RBoardVO vo) {
		List<RBoardVO> rboardList = new ArrayList<RBoardVO>();
		
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_LIST);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RBoardVO rboard = new RBoardVO();
				rboard.setSeq(rs.getInt(1));
				rboard.setTitle(rs.getString(2));
				rboard.setWriter(rs.getString(3));
				rboard.setContent(rs.getString(4));
				rboard.setRegdate(rs.getString(5));
				rboard.setCnt(rs.getInt(6));
				rboardList.add(rboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(rs, pstmt, conn);
		}
		
		return rboardList;
	}
}
