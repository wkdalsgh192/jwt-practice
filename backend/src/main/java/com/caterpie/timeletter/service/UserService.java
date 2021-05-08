package com.caterpie.timeletter.service;

import java.util.Optional;

import com.caterpie.timeletter.dto.JoinDto;
import com.caterpie.timeletter.dto.UserModifyDto;
import com.caterpie.timeletter.entity.User;

public interface UserService {
	/* C :: 회원 가입 */
	void insertUser(JoinDto joinReq);
	
	/* R :: 회원 정보 가져오기 */
	
	/* U :: 회원 가입 수정 */
	void updateUser(UserModifyDto modReq);
	
	/* D :: 회원 정보 삭제 */
	void deleteUser(int userId);
	
	Optional<User> getUserWithAuthorities(String email);
	
	Optional<User> getMyUserWithAuthorities();
}
