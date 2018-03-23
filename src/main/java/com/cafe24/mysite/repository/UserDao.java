package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDaoException;
import com.cafe24.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean update(UserVo authUser, UserVo vo, String originPwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", vo.getName());
		map.put("gender", vo.getGender());
		map.put("password", vo.getPassword());
		map.put("no", authUser.getNo());
		map.put("originPwd", originPwd);
		int count = sqlSession.update("user.update", map);

		return count == 1;
	}
	
	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);
	}
	
	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo", no);
	}
	
	public UserVo get(UserVo vo) throws UserDaoException {
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword", vo);
		
		return result;
	}

	public boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		
		return count == 1;
	}
}
