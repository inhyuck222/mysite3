package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class BoardDao {
	
	@Autowired
	SqlSession sqlSession;
	
	
	/*
	 * g_no(2) = 유지 => 2
	 * o_no(1) = o_no(1) + 1 => 2
	 * d(0) = d + 1 => 1
	 * update o_no = o_no+1 where g_no = 2 and o_no >= 2
	 * insert (~~~~ d(d(0) + 1) ~~~~~~)
	 */
	public boolean insertRepost(BoardVo repostBoard, BoardVo parentBoard, UserVo authUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", repostBoard.getTitle());
		map.put("deleted", repostBoard.isDeleted());
		map.put("content", repostBoard.getContent());
		map.put("groupNo", parentBoard.getGroupNo());
		map.put("orderNo", parentBoard.getOrderNo() + 1);
		map.put("depth", parentBoard.getDepth() + 1);
		map.put("userNo", authUser.getNo());
		int count = sqlSession.insert("insertRepost", map);
		
		repostBoard.setNo((Long)(map.get("no")));
		
		return count == 1;
	}
		
	
	public boolean repostUpdate(Long pGroupNo, Long pOrderNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pGroupNo", pGroupNo);
		map.put("pOrderNo", pOrderNo);
		
		int count = sqlSession.update("board.repostUpdate", map);
		
		return count == 1;
	}
	
	
	public boolean insertNewPost(BoardVo vo) {
		int count = sqlSession.insert("insertNewPost", vo);
		
		return count == 1;
	}
		
		
	public boolean deletePost(Long no) {
		int count = sqlSession.delete("board.deletePost", no);
		
		return count == 1;
	}
	
	
	public boolean updatePost(BoardVo newVo) {
		int count = sqlSession.update("board.updatePost", newVo);
		
		return count == 1;
	}
	
		
	public BoardVo getBoard(Long no) {
		BoardVo theBoard = sqlSession.selectOne("board.selectTheBoard", no);
		
		
		updateHit(no);
		
		
		return theBoard;
	}
	
	
	public boolean updateHit(Long no) {
		int count = sqlSession.update("board.updateHit", no);
		
		return count == 1;
	}
	
	
	public List<BoardVo> select(Long recentPage, Long listCount, Integer pageCount, String keyword){
		Map<String, Object> map = new HashMap<String, Object>();
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		map.put("keyword", keyword);
		map.put("start", (recentPage - 1) * pageCount);
		map.put("listCount", listCount);
		
		
		List<BoardVo> list = sqlSession.selectList("board.selectAllBoardInPage", map);
		
		
		return list;
	}
	
	public Long getTotalCount(String keyword) {
		if(keyword == null || "".equals(keyword)) {
			keyword = "%";
		}
		keyword = "%" + keyword + "%";
		Long totalCount = sqlSession.selectOne("board.getTotalCount", keyword);
		
		return totalCount;
	}


}
