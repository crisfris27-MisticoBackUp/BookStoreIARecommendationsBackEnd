package com.chris.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chris.library.dao.RecomendationRepository;
import com.chris.library.entities.Recomendation;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RecomendationService {
	
	private RecomendationRepository recomendationRepository;
	
	public RecomendationService(RecomendationRepository recomendationRepository) {
		this.recomendationRepository=recomendationRepository;
	}
	
	
	public Recomendation saveMyRecomendation(Recomendation myrecomendation)throws Exception {
		
		Recomendation TheRecomendation = recomendationRepository.save(myrecomendation);
		if(TheRecomendation==null) {
			throw new Exception("the recomendation is null, please check the logic");
		}
		return TheRecomendation;
	}
	
	
	public List<Recomendation> getUserPreferences(String userEmail) throws Exception {
	    List<Recomendation> recomendation = recomendationRepository.findByUserEmail(userEmail);
	    
	    if (recomendation==null) {
	        throw new Exception("User preferences not found");
	    }
	    
	    return recomendation;
	}
	

}
