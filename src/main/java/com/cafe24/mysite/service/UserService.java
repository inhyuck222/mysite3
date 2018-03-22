package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void join(UserVo user){
		userDao.insert(user);
	}
	
	public UserVo getUser(UserVo user) {
		return userDao.get(user);		
	}
	
	public boolean modifyUser(UserVo authUser, UserVo userModified, String originPwd) {
		boolean result = userDao.update(authUser, userModified, originPwd);
		return result;
	}
}
