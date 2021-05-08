package com.caterpie.timeletter.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caterpie.timeletter.dto.JoinDto;
import com.caterpie.timeletter.dto.LoginDto;
import com.caterpie.timeletter.dto.TokenDto;
import com.caterpie.timeletter.dto.UserModifyDto;
import com.caterpie.timeletter.entity.User;
import com.caterpie.timeletter.jwt.JwtFilter;
import com.caterpie.timeletter.jwt.TokenProvider;
import com.caterpie.timeletter.repository.UserRepository;
import com.caterpie.timeletter.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * @apiNote 회원 전체 조회
	 */
	@ApiOperation(value= "Get All Users", notes="상세 조회")
	@GetMapping("/findAll")
	public List<User> FindAll() throws Exception {
		return userRepository.findAll();	
	}
	
	/**
	 * @apiNote 회원가입(*이메일 인증 추가해야함)
	 * @return HttpStatus
	 */
	@Transactional()
	@ApiOperation(value = "Insert User Info", notes = "회원가입")
	@PostMapping("/join")
	public ResponseEntity<?> createUser(@RequestBody JoinDto joinDto) {
		try {
			userService.insertUser(joinDto);
			
		} catch (Exception e) {
			return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);	
		}
		return new ResponseEntity<String>("success",HttpStatus.CREATED);
	}
	
	/**
	 * @apiNote 회원 정보 상세 조회
	 * @return User
	 */
	@ApiOperation(value= "Get User Detail", notes="상세 조회")
	@GetMapping("/detail")
	public Optional<User> detailUser(int userId) throws Exception {
		return  userRepository.findById(userId);	
	}
	
	
	/**
	 * @apiNote 회원 정보 수정(비밀번호 수정)
	 */
	@ApiOperation(value= "Update User Info", notes="회원 정보 수정")
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody UserModifyDto modReq) {
		try {
			userService.updateUser(modReq);
		} catch (Exception e) {
			return new ResponseEntity<String>("회원 정보를 수정할 수 없습니다.", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("회원 정보 수정 완료", HttpStatus.OK);
	}
	
	/**
	 * @apiNote 회원 정보 삭제
	 */
	@ApiOperation(value="Delete User Info", notes="회원 정보 삭제")
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestParam("id") int userId) {
		try {
			userService.deleteUser(userId);
		} catch (Exception e) {
			return new ResponseEntity<String>("회원 정보를 찾을 수 없습니다.",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("삭제 완료", HttpStatus.OK);
	}
	
	/**
	 * @apiNote 로그인
	 * @return JWT
	 */
	@ApiOperation(value= "Login", notes = "로그인")
	@ApiImplicitParams({
		@ApiImplicitParam(name="Authorization", value="authorization header", required=false, dataType="string",
				paramType="header",defaultValue="bearer ")
	})
	@PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {

		// 해시한 비밀번호 가져오기
//		String password = SaltSHA256.getEncrypt(loginDto.getPassword(), 
//				userRepository.findByEmail(loginDto.getEmail()).getSalt());
		
		// 로그인 정보를 이용하여 토큰 객체를 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        // 토큰을 이용해 authenticate가 실행이 될 때 CustomUserDetailsService에서 loadUserByUsername 메소드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        
        // 여기서 authentication 객체가 생성되고, 이를 SecurityContext에 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 또, authentication을 기반으로 jwt 토큰을 생성한다.
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}

