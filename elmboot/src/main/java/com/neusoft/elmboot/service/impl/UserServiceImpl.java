package com.neusoft.elmboot.service.impl;

import com.neusoft.elmboot.po.UserImg;
import com.neusoft.elmboot.po.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.UserService;
import sun.security.util.Password;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByIdByPass(User user) {
		return userMapper.getUserByIdByPass(user);
	}
	
	@Override
	public User getUserById(String userId) {
		return userMapper.getUserById(userId);
	}
	
	@Override
	public int saveUser(User user) {
		return userMapper.saveUser(user);
	}

	@Override
	public int changeUserPortrait(UserImg userImg){
		return userMapper.changeUserPortrait(userImg.getUserId(), userImg.getBase64());
	}

	@Override
	public int modifyPassword(UserPassword userpassword) {
		return userMapper.modifyPassword(userpassword);
	}
}
