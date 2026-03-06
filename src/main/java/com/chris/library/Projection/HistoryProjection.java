package com.chris.library.Projection;

import org.springframework.data.rest.core.config.Projection;

import com.chris.library.entities.History;


@Projection(name = "HistoryProjection", types = { History.class })
public interface HistoryProjection {
	Long getId();
	String getUserEmail();
	String getCheckoutDate();
	String getReturnedDate();
	String getTitle();
	String getAuthor();
	String getDescription();
	String getImg();

}
