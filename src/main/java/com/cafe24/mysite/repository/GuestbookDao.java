package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean delete(GuestbookVo vo) {
		int count = sqlSession.delete("guestbook.delete", vo);
		
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
	
	public List<GuestbookVo> getList(Long no){
		List<GuestbookVo> list = sqlSession.selectList("guestbook.getListByNo", no);
		
		return list;
	}
	
	public GuestbookVo get(Long no) {
		return sqlSession.selectOne("guestbook.getByNo", no);
	}
}
