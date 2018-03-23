package com.cafe24.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.vo.UserVo;


@Controller
public class MainController {
		
	@RequestMapping(value={"/main", ""})
	public String main() {
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "안녕";
	}
	
	@ResponseBody
	@RequestMapping("/hello2")
	public JSONResult hello2() {
		
		return JSONResult.success(new UserVo());
		
	}
	
}
