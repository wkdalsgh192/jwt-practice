package com.caterpie.timeletter.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caterpie.timeletter.entity.Club;
import com.caterpie.timeletter.repository.ClubRepository;
import com.caterpie.timeletter.service.ClubService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/club")
public class ClubController {
	static final Logger logger = LoggerFactory.getLogger(ClubController.class);
	@Autowired
	private ClubRepository clubRepository;
	@Autowired
	ClubService service;
	
	/* 클럽 생성 */
	@Transactional()
	@PostMapping(path="/insert")
	public ResponseEntity<?> insertClub(@RequestBody Club club) {
		try {
			service.insertClub(club);
		}catch (Exception e) {
			return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);		
		}
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}
	
	@GetMapping("/findAll")
	public List<Club> findAll() {
		return clubRepository.findAll();
	}
	
	@GetMapping("/findByName")
	public Club findByName(@RequestParam String name) {
		return clubRepository.findByClubName(name);
	}
	
	@ApiOperation(value = "club_id로 삭제", response = String.class)
	@DeleteMapping("/delClub")
	public ResponseEntity<?> delpost(@RequestParam("id") int clubId) {
		try {
			clubRepository.deleteById(clubId);
        }catch (Exception e) {
        	return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}   	
    	return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
}
