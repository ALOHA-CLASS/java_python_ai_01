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
	public List<Board> list() throws Exception {
		
		// BoardMapper 의 list() 메소드 호출

		List<Board> boardList = boardMapper.list();
		
		return boardList;
	}

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
	public int delete(List<String> boardNoList) throws Exception {
		
		String noList = "";
		for (int i = 0; i < boardNoList.size(); i++) {
			noList += boardNoList.get(i);
			if( i+1 != boardNoList.size() ) {
				noList += ", ";
			}
		}
		log.info("noList : " +  noList );
		
		int result = boardMapper.delete(noList);
		return result;
	}

	@Override
	public List<Board> list(String keyword) throws Exception {
		
		// 검색어가 없을 때
		if( keyword == null ) keyword = "";
		
		List<Board> boardList = boardMapper.search(keyword);
		return boardList;
	}

	@Override
	public List<Board> list(Page page) throws Exception {
		// 전체 게시글 수
		int totalCount = boardMapper.count();
		log.info("totalCount : " + totalCount);
		
		// 페이징 처리
		page.setTotalCount(totalCount);
		page.calc(page);
		
		List<Board> boardList = boardMapper.page(page);
		
		return boardList;
	}

	@Override
	public int count() throws Exception {
		int count = boardMapper.count();
		return count;
	}

	@Override 
	public List<Board> list(Page page, String keyword) throws Exception {
//		
//		// 검색어가 없을 때
//		if(keyword == null) keyword = "";
//		
//		// 검색어 포함 게시글 수
//		int totalCount = boardMapper.countWithKeyword(keyword);
//		log.info("totalCount : " + totalCount);
//		
//		// 페이징 처리
//		page.setTotalCount(totalCount);
//		page.calc(page);
//		
//		List<Board> boardList = boardMapper.boardList(page, keyword);
//		
//		return boardList;
		return null;
	} 
//	List<Board> list(Page page, Option option) 로 전환

	@Override
	public List<Board> list(Page page, Option option) throws Exception {
		// 검색어가 없을 때
//		if(option.getKeyword() == null) option.getKeyword() = "";
//		생성자에서 ""를 정의하여 필요없음
		
		// 검색어 포함 게시글 수
		int totalCount = boardMapper.countWithKeyword(option);
		log.info("totalCount : " + totalCount);
		
		// 페이징 처리
		page.setTotalCount(totalCount);
		page.calc(page);
		
		List<Board> boardList = boardMapper.boardList(page, option);
		
		return boardList;
	}


}











