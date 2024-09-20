package com.neusoft.elmboot.controller;

import com.neusoft.elmboot.po.UserImg;
import com.neusoft.elmboot.po.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.UserService;

import static com.neusoft.elmboot.controller.Lock_info.user_lock;
import static com.neusoft.elmboot.util.MD5.encode;

@RestController
@RequestMapping("/UserController")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getUserByIdByPass")
	public User getUserByIdByPass(User user) throws Exception{
		user.setPassword(encode(user.getPassword()));
		return user_lock(userService.getUserByIdByPass(user));
	}

	@RequestMapping("/getUserById")
	public User getUserById(User user) throws Exception{
		return user_lock(userService.getUserById(user.getUserId()));
	}

	@RequestMapping("/saveUser")
	public int saveUser(User user) throws Exception{
		user.setPassword(encode(user.getPassword()));
		return userService.saveUser(user);
	}

	@RequestMapping("/changeUserPortrait")
	public int changeUserPortrait(UserImg userImg) throws Exception{
		return userService.changeUserPortrait(userImg);
	}

	@RequestMapping("/modifyPassword")
	public boolean modifyPassword(UserPassword userPassword) throws Exception {
		userPassword.setNewPassword(encode(userPassword.getNewPassword()));
		userPassword.setOldPassword(encode(userPassword.getOldPassword()));
		int i = userService.modifyPassword(userPassword);
		if(i == 1){
			return true;
		}
		return false;
	}

}