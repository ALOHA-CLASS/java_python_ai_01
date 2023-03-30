package com.human.project.service;

import java.util.List;

import com.human.project.domain.Board;
import com.human.project.domain.Option;
import com.human.project.domain.Page;

public interface BoardService {
	
	// 게시글 쓰기
	public int insert(Board board) throws Exception;
	
	// 게시글 읽기
	public Board read(int boardNo) throws Exception;
	
	// 게시글 수정
	public int update(Board board) throws Exception;
	
	// 게시글 삭제
	public int delete(int boardNo) throws Exception;

	// [검색 + 옵션][페이지] 게시글 목록
	public List<Board> list(Page page, Option option) throws Exception;

	// 조회수
	public int joinCntUp(int boardNo) throws Exception;

}

