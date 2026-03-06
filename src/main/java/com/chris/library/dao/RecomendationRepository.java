package com.chris.library.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chris.library.Projection.RecommendationProjection;
import com.chris.library.entities.Recomendation;

@RepositoryRestResource(excerptProjection = RecommendationProjection.class)
public interface RecomendationRepository extends JpaRepository<Recomendation,Long>{
    List<Recomendation> findByUserEmail(@Param("userEmail") String userEmail);

	
}
