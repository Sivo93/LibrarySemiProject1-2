package com.example.myweb.rboard;

import java.util.List;

public interface RBoardService {
	RBoardVO getOne(RBoardVO vo);
	List<RBoardVO> getList(RBoardVO vo);
	
	void insert(RBoardVO vo);
	void update(RBoardVO vo);
	void delete(RBoardVO vo);
}