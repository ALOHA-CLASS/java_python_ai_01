package com.human.project.service;

import java.util.List;

import com.human.project.domain.Comment;

public interface CommentService {

	// 댓글 전체 조회
	public List<Comment> list() throws Exception;
	
	// 댓글 목록 - 게시글 번호
	public List<Comment> list(int boardNo) throws Exception;
	
	// 댓글 등록
	public int insert(Comment reply) throws Exception;
	
	// 댓글 수정
	public int update(Comment reply) throws Exception;
	
	// 댓글 삭제
	public int delete(int replyNo) throws Exception;
	
	// 답글 등록
	public int insertAnswer(Comment reply) throws Exception;
	
	
}
