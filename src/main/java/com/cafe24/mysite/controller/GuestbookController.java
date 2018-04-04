package com.cafe24.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@RequestMapping("guestbook")
@Controller
public class GuestbookController {
	
	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping(value= {"", "/list"})
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getGustbookList();
		model.addAttribute("list", list);
		return "/guestbook/list";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestbookVo vo) {
		if(vo == null) {
			return "redirect:/guestbook/list";
		}
		
		boolean result = guestbookService.insertGuestbook(vo);
		if(result == false) {
			return "redirect:/guestbook/list";	
		}
		
		return "redirect:/guestbook/list";		
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@RequestParam(value="no") Long no, Model model) {
		if(no == null) {
			return "redirect:/guestbook/list";	
		}
		
		model.addAttribute("no", no);
		
		return "/guestbook/deleteform";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo, Model model) {
		if(vo == null) {
			return "redirect:/guestbook/list";	
		}

		boolean result = guestbookService.deleteGuestbookVo(vo);
		if(result == false) {
			return "redirect:/guestbook/list";
		}
		
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping("/ajax")
	public String ajax() {
		return "guestbook/index-ajax";
	}
}
