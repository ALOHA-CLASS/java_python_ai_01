package com.human.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.human.project.domain.Comment;

@Mapper
public interface CommentMapper {
	
	// 댓글 전체 조회
	public List<Comment> list() throws Exception;
	
	// 댓글 목록 - 게시글 번호
	public List<Comment> listByBoardNo(int boardNo) throws Exception;
	
	// 댓글 등록
	public int insert(Comment comment) throws Exception;
	
	// 댓글 수정
	public int update(Comment comment) throws Exception;
	
	// 댓글 삭제
	public int delete(int commentNo) throws Exception;

	// [최댓값] 댓글번호
	public int maxCommentNo() throws Exception;

	// 순서번호 갱신
	public int syncSeqNo(Comment comment) throws Exception;

	// 부모의 자식개수 갱신
	public void syncSubCount(@Param("parentNo") int parentNo, @Param("no") int no) throws Exception;

	// 댓글 조회
	public Comment select(int commentNo) throws Exception;
	
		

}



















