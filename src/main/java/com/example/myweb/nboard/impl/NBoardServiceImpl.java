package com.example.myweb.nboard.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.nboard.NBoardService;
import com.example.myweb.nboard.NBoardVO;

@Service("nboardService")
public class NBoardServiceImpl implements NBoardService {
	@Autowired
	private NBoardDAO nboardDao;
	
	//@Autowired
	//@Qualifier("logAdvice")
	//@Inject
	//private LogAdvice logAdvice;

	@Override
	public NBoardVO getOne(NBoardVO vo) {
		//logAdvice.printLog();
		return nboardDao.getNBoard(vo);
	}
	

	@Override
	public List<NBoardVO> getList(NBoardVO vo) {
		//logAdvice.printLog();
		return nboardDao.getNBoardList(vo);
	}

	@Override
	public void insert(NBoardVO vo) {
		//logAdvice.printLog();
		nboardDao.insertBoard(vo);
	}

	@Override
	public void update(NBoardVO vo) {
		//logAdvice.printLog();
		nboardDao.updateBoard(vo);
	}

	@Override
	public void delete(NBoardVO vo) {
		//logAdvice.printLog();
		nboardDao.deleteBoard(vo);
	}

}