package com.example.myweb.nboard;

import java.util.List;

public interface NBoardService {
	NBoardVO getOne(NBoardVO vo);
	List<NBoardVO> getList(NBoardVO vo);
	
	void insert(NBoardVO vo);
	void update(NBoardVO vo);
	void delete(NBoardVO vo);
}