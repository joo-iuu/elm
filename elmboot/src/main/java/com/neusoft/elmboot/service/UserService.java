package com.neusoft.elmboot.service;

import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserImg;
import com.neusoft.elmboot.po.UserPassword;
import org.apache.ibatis.annotations.Param;

public interface UserService {

	public User getUserByIdByPass(User user);
	public User getUserById(String userId);
	public int saveUser(User user);
	public int changeUserPortrait(UserImg userImg);
	public int modifyPassword(UserPassword userPassword);
}
