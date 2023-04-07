package com.human.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.project.domain.Board;
import com.human.project.domain.Option;
import com.human.project.domain.Page;
import com.human.project.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public int insert(Board board) throws Exception {
		int result = boardMapper.insert(board);
		return result;
	}

	@Override
	public Board read(int boardNo) throws Exception {
		Board board = boardMapper.read(boardNo);
		return board;
	}

	@Override
	public int update(Board board) throws Exception {
		int result = boardMapper.update(board);
		return result;
	}

	@Override
	public int delete(int boardNo) throws Exception {
		int result = boardMapper.delete(boardNo);
		return result;
	}

	@Override
	public List<Board> list(Page page, Option option) throws Exception {
		
		// 검색어 포함 게시글 수
		int totalCount = boardMapper.countWithKeyword(option);
		log.info("totalCount : " + totalCount);
		
		// 페이징 처리
		page.setTotalCount(totalCount);
		page.calc(page);
		
		List<Board> boardList = boardMapper.boardList(page, option);
		
		return boardList;
	}

	@Override
	public int joinCntUp(int boardNo) throws Exception {
		int result = boardMapper.joinCntUp(boardNo);
		
		return result;
	}


}











