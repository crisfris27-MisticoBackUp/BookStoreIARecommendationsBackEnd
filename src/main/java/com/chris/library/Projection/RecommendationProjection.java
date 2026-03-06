package com.chris.library.Projection;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.chris.library.entities.Recomendation;


@Projection(name = "RecomendationProjection", types = { Recomendation.class })
public interface RecommendationProjection {

    Long getId();
    String getUserEmail();
    String getFavoriteGenres();
    String getReadingFrequency();
    String getPreferredLength();
    List<String> getInterests();

	
}
