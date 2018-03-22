package com.cafe24.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Service
public class BoardService {
	private static final Long LIST_COUNT = 10L;
	private static final Integer PAGE_COUNT = 10;

	@Autowired
	private BoardDao boardDao;
	
	public boolean deletePost(Long no) {
		boolean result = boardDao.deletePost(no);
		
		return result;
	}
	
	public boolean modifyPost(BoardVo boardForModified) {
		boolean result = boardDao.updatePost(boardForModified);
		
		return result;
	}
	
	public boolean insertNewpost(BoardVo newpostBoard, UserVo writer) {
		newpostBoard.setUserNo(writer.getNo());
		newpostBoard.setOrderNo(1L);
		newpostBoard.setDepth(0L);
		newpostBoard.setHit(0L);
		boolean result = boardDao.insertNewPost(newpostBoard);
		
		return result;
	}
	
	public BoardVo getParentBoard(Long parentNo) {
		BoardVo parentBoard = boardDao.getBoard(parentNo);
		
		return parentBoard;
	}
	
	public boolean insertRepost(BoardVo repostBoard, BoardVo parentBoard, UserVo authUser) {		
		boolean result = boardDao.insertRepost(repostBoard, parentBoard, authUser);
		
		return result;
	}
	
	public BoardVo viewTheBoard(Long no) {
		BoardVo boardSelected = boardDao.getBoard(no);
		
		return boardSelected;
	}
	
	public Map<String, Object> getList(String keyword, Long recentPage){		
		Long totalCount = getTotalCount(keyword);
		Long totalPage = totalCount / LIST_COUNT;
				
		if(totalPage % LIST_COUNT > 0) {
			totalPage += 1;
		}
		
		if(recentPage > totalPage) {
			recentPage = totalPage;
		}else if(recentPage < 1) {
			recentPage = 1L;
		}
		
		if(totalPage < recentPage) {
			recentPage = totalPage;
		}
		
		
		Long startPage = ((recentPage - 1) / PAGE_COUNT) * PAGE_COUNT + 1;
		Long endPage = startPage + PAGE_COUNT - 1;
		
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		
		Long[] pages = new Long[PAGE_COUNT];
		long pageIndex = startPage;
		
		for(int index = 0; pageIndex <= PAGE_COUNT; index++) {
			pages[index] = pageIndex++;
		}
		
		if(recentPage == 0) {
			recentPage = 1L;
		}
		
		List<BoardVo> list = boardDao.select(recentPage, LIST_COUNT, PAGE_COUNT, keyword);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", keyword);
		map.put("totalCount", totalCount);
		map.put("listCount", LIST_COUNT);
		map.put("pageCount", PAGE_COUNT);
		map.put("pages", pages);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("recentPage", recentPage);
		map.put("list", list);
		
		
		return map;
	}
	
	public Long getTotalCount(String keyword) {
		Long totalCount = boardDao.getTotalCount(keyword);
		if(totalCount == null) {
			totalCount = 0L;
		}
		
		
		return totalCount;
	}
	
}
