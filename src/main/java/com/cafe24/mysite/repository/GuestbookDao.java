package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean delete(GuestbookVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", vo.getNo());
		map.put("pwd", vo.getPassword());
		int count = sqlSession.delete("guestbook.delete", map);
		
		return count == 1;
	}
	
	public boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		
		return count == 1;
	}
	
	public List<GuestbookVo> getList(){
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getList");
		
		return list;
	}
}
