package com.cafe24.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Auth
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(UserVo user) {
		return "/user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@Valid UserVo user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			
			
			/*
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors) {
				System.out.println("Object Error : " + error);
			}
			*/
			return "/user/join";
		}
		
		userService.join(user);
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, Model model) {
		
		model.addAttribute(authUser);
		
		return "/user/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, @ModelAttribute UserVo userModified, @RequestParam(value="originpwd") String originPwd) {		
		if(authUser == null) {
			return "redirect:/main";
		}
		
		boolean result = userService.modifyUser(authUser, userModified, originPwd);
		if(result == false) {
			return "/user/modify";
		}
		
		return "redirect:/main";
	}
	
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(HttpSession session, @ModelAttribute UserVo user, Model model) {
//		UserVo authUser = userService.getUser(user);
//		if(authUser == null) {
//			model.addAttribute("result", "fail");
//			return "/user/login";
//		}
//		
//		//인증 처리
//		session.setAttribute("authUser", authUser);
//		
//		return "redirect:/main";
//	}
	
//	@RequestMapping("logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/main";
//	}
	
}
