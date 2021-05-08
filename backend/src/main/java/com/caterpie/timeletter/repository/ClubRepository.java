package com.caterpie.timeletter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpie.timeletter.entity.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {
	Club findByClubName(String clubName);
	
}

