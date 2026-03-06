package com.chris.library.Projection;

import org.springframework.data.rest.core.config.Projection;

import com.chris.library.entities.Messages;


@Projection(name = "MessagesProjection", types = { Messages.class })
public interface MessagesProjection {

	Long getId();
	
	String getTitle();
	
	String getUserEmail();

	String getQuestion();
	
	String getAdminEmail();
	
	String getResponse();
	
	String isClosed();
}
