package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	GuestbookDao guestbookDao;
	
	public List<GuestbookVo> getGustbookList(Long no){
		List<GuestbookVo> list = guestbookDao.getList(no);
		
		return list;
	}
	
	public List<GuestbookVo> getGustbookList(){
		List<GuestbookVo> list = guestbookDao.getList();
		
		return list;
	}
	
	public boolean insertGuestbook(GuestbookVo vo) {
		boolean result = guestbookDao.insert(vo);
		
		return result;
	}
	
	public GuestbookVo insertGuestbookByAjax(GuestbookVo guestbookVo) {
		GuestbookVo vo = null;
		boolean result = guestbookDao.insert(guestbookVo);
		if(result == true) {
			vo = guestbookDao.get(guestbookVo.getNo());
		}
		
		return vo;
	}
	
	public boolean deleteGuestbookVo(GuestbookVo vo) {
		boolean result = guestbookDao.delete(vo);
		
		return result;		
	}

}
