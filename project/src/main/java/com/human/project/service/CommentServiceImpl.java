package com.human.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.project.domain.Comment;
import com.human.project.mapper.CommentMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<Comment> list() throws Exception {
		List<Comment> commentList = commentMapper.list();
		
		
		for (Comment comment : commentList) {
			// log.info(comment.toString());
		}
		
		
		return commentList;
	}

	@Override
	public List<Comment> list(int boardNo) throws Exception {
		List<Comment> commentList = commentMapper.listByBoardNo(boardNo);
		return commentList;
	}

	@Override
	public int insert(Comment comment, String userId) throws Exception {
		
		int result = commentMapper.insert(comment);
		
		// 댓글 번호 최댓값
		int commentNo = commentMapper.maxCommentNo();
		// log.info("commentNo : " + commentNo);
		comment.setUserId(userId);
		comment.setCommentNo(commentNo);
		comment.setGroupNo(commentNo);
		
		result = commentMapper.update(comment);
		
		
		return result;
	}

	@Override
	public int update(Comment comment) throws Exception {
		int result = commentMapper.update(comment);
		return result;
	}

	@Override
	public int delete(int commentNo) throws Exception {
		int result = 0;
		
		// 삭제할 댓글 정보 조회
		Comment comment = commentMapper.select(commentNo);
		
		int subCnt = comment.getSubCnt();
		// 자식 댓글이 없으면, 삭제
		if( subCnt == 0 ) {
			// 삭제요청
			result = commentMapper.delete(commentNo);
		}
		// 자식 댓글이 있으면, "삭제된 게시글 입니다" 로 내용 수정
		else {
			comment.setContent("삭제된 댓글입니다.");
			result = commentMapper.update(comment);
			return result;
		}
		
		// 댓글 삭제시, 부모댓글의 자식 개수 - 1 
		int parentNo = comment.getParentNo();
		syncsubCnt(parentNo, -1);
		
		return result;
	}

	@Override
	public int insertAnswer(Comment comment, String userId) throws Exception {
		
		// 계층번호 + 1
		int depthNo = comment.getDepthNo() + 1;
		// 순서번호  = 부모 댓글의 순서번호 + 부모 댓글의 자식개수 + 1
		int seqNo = comment.getSeqNo() + comment.getSubCnt() + 1;
		
		comment.setUserId(userId);
		comment.setDepthNo(depthNo);
		comment.setSeqNo(seqNo);
		
		// 뒤의 순서번호 + 1
		commentMapper.syncSeqNo(comment);
		
		// 답글 등록
		int result = commentMapper.insert(comment);
		
		// 부모글의 자식 개수 + 1
		int parentNo = comment.getParentNo();
		syncsubCnt(parentNo, 1);
		
		
		return result;
	}
	
	// 부모 댓글의 자식 개수 갱신
	public void syncsubCnt(int parentNo, int no) throws Exception {
		
		// 부모 정보 조회
		Comment comment = commentMapper.select(parentNo);
		
		if( comment == null ) return;
		
		// 조상 번호
		int ancestorNo = comment.getParentNo();
		
		// 부모 댓글 자식개수 갱신
		commentMapper.syncSubCnt(parentNo, no);

		// (종료조건) 원본 댓글에서 멈춤
		if( ancestorNo == 0 ) return;
		
		// 재귀 호출
		syncsubCnt(ancestorNo, no);
		
	}

}




















