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

// @Repository("boardDAO")
public class NBoardDAOJdbc {
	@Resource(name="jdbcUtil")
	private JDBCUtil jdbcUtil;
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	final String SQL_GET = "select * from noti_board where seq=?";
	final String SQL_LIST = "select * from noti_board order by seq desc";
	final String SQL_INSERT = "insert into noti_board(seq, title, writer, content) " +
						"values((select nvl(max(seq),0)+1 from noti_board),?,?,?)";
	final String SQL_UPDATE = "update noti_board set title=?, content=? where seq=?";
	final String SQL_DELETE = "delete from noti_board where seq=?";
	
	// 등록
	public void insertNBoard(NBoardVO vo) {
		
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
	public void updateNBoard(NBoardVO vo) {
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
	public void deleteNBoard(NBoardVO vo) {
		
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
	public NBoardVO getNBoard(NBoardVO vo) {
		NBoardVO nboard = null;
		
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_GET);
			pstmt.setInt(1, vo.getSeq());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				nboard = new NBoardVO();
				nboard.setSeq(rs.getInt(1));
				nboard.setTitle(rs.getString(2));
				nboard.setWriter(rs.getString(3));
				nboard.setContent(rs.getString(4));
				nboard.setRegdate(rs.getString(5));
				nboard.setCnt(rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(rs, pstmt, conn);
		}
		
		return nboard;
	}
	
	// 글 목록 조회
	public List<NBoardVO> getNBoardList(NBoardVO vo) {
		System.out.println("===> getNBoardList() - NBoardDAO 기능 처리");
		List<NBoardVO> nboardList = new ArrayList<NBoardVO>();
		
		try {
			conn = jdbcUtil.getConnection();
			pstmt = conn.prepareStatement(SQL_LIST);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NBoardVO nboard = new NBoardVO();
				nboard.setSeq(rs.getInt(1));
				nboard.setTitle(rs.getString(2));
				nboard.setWriter(rs.getString(3));
				nboard.setContent(rs.getString(4));
				nboard.setRegdate(rs.getString(5));
				nboard.setCnt(rs.getInt(6));
				nboardList.add(nboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close(rs, pstmt, conn);
		}
		
		return nboardList;
	}
}
