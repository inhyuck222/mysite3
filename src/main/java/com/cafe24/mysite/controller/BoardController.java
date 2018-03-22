package com.cafe24.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@RequestMapping("board")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	
	@RequestMapping(value= {"", "list"})
	public String list(
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword, 
			@RequestParam(value="page", required=true, defaultValue="1") Long recentPage,
			Model model)
	{
		
		Map<String, Object> map = boardService.getList(keyword, recentPage);
		
		model.addAttribute("map", map);
		
		return "/board/list";
	}
	
	
	
	@RequestMapping(value="view")
	public String view(
			@RequestParam(value="no") Long no, 
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword,
			@RequestParam(value="page", required=true, defaultValue="1") Long recentPage,
			Model model)
	{
		BoardVo boardSelected = boardService.viewTheBoard(no);
		if(boardSelected == null) {
			model.addAttribute("recentPage", recentPage);
			model.addAttribute("kwd", keyword);
			
			return "/board/list";
		}
		
		model.addAttribute("board", boardSelected);
		
		return "/board/view";
	}

	
	
	@RequestMapping(value="newpostwrite", method=RequestMethod.GET)
	public String newpostWrite(HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			System.out.println("BoardController.newpostWrite : authUser is null");
			return "/user/login";
		}		
		
		return "/board/newpostwrite";
	}
	
	
	
	//newboard의 no를 받아서 view로
	@RequestMapping(value="newpostwrite", method=RequestMethod.POST)
	public String newpostWrite(
			HttpSession session, 
			Model model, 
			BoardVo newpostBoard, 
			@RequestParam(value="page", required=true, defaultValue="1") Long recentPage
			) 
	{
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			System.out.println("BoardController.newpostWrite : authUser is null");
			return "/user/login";
		}
		
		boolean result = boardService.insertNewpost(newpostBoard, authUser);
		model.addAttribute("page", recentPage);		//수정 후 삭제
		if(result == false) {
			//model.addAttribute("page", recentPage);  수정 후 추가
			return "redirect:/board/list";
		}
		
		
		return "redirect:/board/list";
	}
	
	
	
	/*
	 * 	새로 작성된 보드의 no를 갖고 와야된다.
	 */
	@RequestMapping(value="repost", method=RequestMethod.GET)
	public String repostWrite(
			Model model, 
			@RequestParam(value="no", required=false) Long parentNo,			
			@RequestParam(value="page", required=true, defaultValue="1") Long recentPage)
	{
		model.addAttribute("page", recentPage);
		if(parentNo == null) {
			System.out.println("해당 글이 존재하지 않습니다.");			
			return "/board/list";
		}
		
		
		BoardVo parentBoard = boardService.getParentBoard(parentNo);		
		if(parentBoard == null) {
			System.out.println("해당 글이 존재하지 않습니다.");			
			return "/board/list";
		}
		
		
		model.addAttribute("parentBoard", parentBoard);
		
		
		return "/board/repostwrite";
	}
	
	
	
	@RequestMapping(value="repost", method=RequestMethod.POST)
	public String repostWrite(
			Model model, 
			HttpSession session, 
			@ModelAttribute BoardVo repostBoard, 
			@RequestParam(value="pGroupNo", required=false) Long pGroupNo, 
			@RequestParam(value="pOrderNo", required=false) Long pOrderNo, 
			@RequestParam(value="pDepth", required=false) Long pDepth, 
			@RequestParam(value="page", required=true, defaultValue="1") Long recentPage) 
	{
		if(session == null) {
			return "/board/list";
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "/user/login";
		}
		
		
		BoardVo parentBoard = new BoardVo();
		parentBoard.setGroupNo(pGroupNo);
		parentBoard.setOrderNo(pOrderNo);
		parentBoard.setDepth(pDepth);
		boolean result = boardService.insertRepost(repostBoard, parentBoard, authUser);
		
		model.addAttribute("page", recentPage);		//수정 후 삭제
		if(result == false) {
			//model.addAttribute("page", recentPage);  수정 후 추가
			return "/board/view";
		}
		
			
		model.addAttribute("board", repostBoard);
		
		
		return "redirect:/board/list";
	}
		
	
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modifyPost(HttpSession session, Model model, @RequestParam(value="boardNo", required=false) Long no) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");		
		
		if(authUser == null) {
			System.out.println("BoardController.modifyPost : authUser is null");
			return "/board/view";
		}
		
		BoardVo boardForModified = boardService.viewTheBoard(no);		
		model.addAttribute("boardVo", boardForModified);
		
		return "/board/modify";
	}
	
	
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modifyPost(HttpSession session, Model model, @ModelAttribute BoardVo boardForModified) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		model.addAttribute("no", boardForModified.getNo());
		if(authUser == null) {
			System.out.println("BoardController.modifyPost : authUser is null");
			return "/board/view";
		}
		
		boolean result = boardService.modifyPost(boardForModified);
		if(result == false) {
			System.out.println("BoardController.modifyPost : can't update the board");			
			return "/board/view";
		}
		model.addAttribute("no", boardForModified.getNo());
		
		return "redirect:/board/view";
	}
	
	
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(
			Model model, 
			HttpSession session, 
			@RequestParam(value="no") Long no, 
			@RequestParam(value="page") Long recentPage)
	{
		
		model.addAttribute("page", recentPage);
	
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null){
			System.out.println("BoardController.modifyPost : authUser is null");
			return "/board/view";
		}
		
		
		boolean result = boardService.deletePost(no);
		if(result == false) {
			System.out.println("BoardController.modifyPost : delete fail");
			return "/board/view";
		}
		
		return "redirect:/board/list";
	}
	
}
