package com.example.myweb.rboard.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myweb.rboard.RBoardService;
import com.example.myweb.rboard.RBoardVO;

@Service("rboardService")
public class RBoardServiceImpl implements RBoardService {
	@Autowired
	private RBoardDAO rboardDao;
	
	//@Autowired
	//@Qualifier("logAdvice")
	//@Inject
	//private LogAdvice logAdvice;

	@Override
	public RBoardVO getOne(RBoardVO vo) {
		//logAdvice.printLog();
		return rboardDao.getRBoard(vo);
	}
	

	@Override
	public List<RBoardVO> getList(RBoardVO vo) {
		//logAdvice.printLog();
		return rboardDao.getRBoardList(vo);
	}

	@Override
	public void insert(RBoardVO vo) {
		//logAdvice.printLog();
		rboardDao.insertBoard(vo);
	}

	@Override
	public void update(RBoardVO vo) {
		//logAdvice.printLog();
		rboardDao.updateBoard(vo);
	}

	@Override
	public void delete(RBoardVO vo) {
		//logAdvice.printLog();
		rboardDao.deleteBoard(vo);
	}

}