package com.caterpie.timeletter.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caterpie.timeletter.controller.SaltSHA256;
import com.caterpie.timeletter.dto.JoinDto;
import com.caterpie.timeletter.dto.UserModifyDto;
import com.caterpie.timeletter.entity.Authority;
import com.caterpie.timeletter.entity.User;
import com.caterpie.timeletter.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
	
	
	@Override
	public void insertUser(JoinDto joinDto) {
		if (userRepo.findOneWithAuthoritiesByEmail(joinDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
		
		Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
		
		
//		// 1. 가입할 회원의 고유 salt 생성 및 저장
		String salt = SaltSHA256.generateSalt();
//		// 2. 입력된 비밀번호 + salt 활용해서 암호화된 비밀번호 생성
//		String password = SaltSHA256.getEncrypt(joinDto.getPassword(), salt);
		
		User user = User.builder()
				.email(joinDto.getEmail())
				.password(passwordEncoder.encode(joinDto.getPassword()))
				.phone(joinDto.getPhone())
				.name(joinDto.getName())
				.salt(salt)
				.activated(true)
				.authorities(Collections.singleton(authority))
				.build();
		

		userRepo.save(user);
	}

	@Override
	public void updateUser(UserModifyDto modReq) {
		
		int userId = modReq.getUserId();
		String salt = SaltSHA256.generateSalt();
		String password = SaltSHA256.getEncrypt(modReq.getPassword(), salt);
		userRepo.updateUser(modReq.getName(), password, salt, modReq.getPhone(), userId);
		
	}
	
	@Override
	public void deleteUser(int userId) {
		userRepo.deleteById(userId);
	}

	@Override
	@Transactional
	public Optional<User> getUserWithAuthorities(String email) {
		return userRepo.findOneWithAuthoritiesByEmail(email);
	}
	
	@Override
	@Transactional
	public Optional<User> getMyUserWithAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//    @Transactional(readOnly = true)
//    public Optional<User> getMyUserWithAuthorities() {
//        return SecurityUtil.getCurrentUsername().flatMap(userRepo::findOneWithAuthoritiesByEmail);
//    }
	
	
}
