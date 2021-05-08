package com.caterpie.timeletter.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.caterpie.timeletter.entity.Club;
import com.caterpie.timeletter.repository.ClubRepository;

@Service
public class ClubServiceImpl implements ClubService {
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Transactional 
	public void insertClub(Club club) { 
		clubRepository.save(club);
	}
	
	@Transactional
	public void deleteClub(@RequestParam int clubId){
		clubRepository.deleteById(clubId);
    }

	
}
