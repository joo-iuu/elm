package com.neusoft.elmboot.mapper;

import com.neusoft.elmboot.po.UserPassword;
import org.apache.ibatis.annotations.*;

import com.neusoft.elmboot.po.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where userId=#{userId} and password=#{password}")
	public User getUserByIdByPass(User user);
	
	@Select("select * from user where userId=#{userId}")
	public User getUserById(String userId);
	
	@Insert("insert into user values(#{userId},#{password},#{userName},#{userSex},null,1)")
	public int saveUser(User user);

	@Update("update user set userImg = #{userImg_base64} where userId = #{userId}")
	public int changeUserPortrait(@Param("userId")String userId,@Param("userImg_base64")String userImg_base64);

    @Update("update user set password = #{newPassword} where userId = #{userId} and password = #{oldPassword}")
	public int modifyPassword(UserPassword userPassword);
}
