package com.cafe24.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller("guestbookAPIController")
@RequestMapping("/api/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;

	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(@RequestParam(value="", required=true, defaultValue="0") Long no) {
		List<GuestbookVo> list = guestbookService.getGustbookList(no);
		
		return JSONResult.success(list);
	}

	// @RequestBody는 String으로 넘어온 Json을 객체로 변경해주는 녀석?
	@ResponseBody
	@RequestMapping("/insert")
	public JSONResult list(@RequestBody GuestbookVo vo) {
		GuestbookVo guestbookVo = guestbookService.insertGuestbookByAjax(vo);
		return JSONResult.success(guestbookVo);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(@ModelAttribute GuestbookVo vo) {
		boolean result = guestbookService.deleteGuestbookVo(vo);
		
		return JSONResult.success(result ? vo.getNo() : -1);
	}

}
