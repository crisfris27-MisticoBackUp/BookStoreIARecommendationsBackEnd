package com.chris.library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chris.library.RequestModels.AdminQuestionRequest;
import com.chris.library.dao.MessageRepository;
import com.chris.library.entities.Messages;

@Service
@Transactional
public class MessagesService {

	private MessageRepository messageRepository;
	
	
	@Autowired
	public MessagesService(MessageRepository messageRepository) {
		this.messageRepository=messageRepository;
	}
	
	
	public void PostMessage(Messages messageRequest, 
			String userEmail) throws Exception {
	    Messages message = new Messages();
	    message.setTitle(messageRequest.getTitle());
	    message.setQuestion(messageRequest.getQuestion());
	    message.setUserEmail(userEmail);
		
		if(message==null) {
			throw new Exception("message is null, please review the logic of the Message");
		}
		System.out.println("this is the object of the Message");
		System.out.println(message);
		message.setUserEmail(userEmail);
		messageRepository.save(message);
			
	}
	
	
	
	public void PutMessage(AdminQuestionRequest adminQuestionRequest, 
			String userEmail) throws Exception {
		if (adminQuestionRequest == null || adminQuestionRequest.getId() == null) {
			throw new Exception("Message id is required");
		}

		if (adminQuestionRequest.getResponse() == null || adminQuestionRequest.getResponse().trim().isEmpty()) {
			throw new Exception("Response is required");
		}
		
		Optional<Messages> message = messageRepository.findById(adminQuestionRequest.getId());

		if (message.isEmpty()) {
		    throw new Exception("Message not found");
		}

		Messages existingMessage = message.get();
		existingMessage.setResponse(adminQuestionRequest.getResponse().trim());
		existingMessage.setAdminEmail(userEmail);
		existingMessage.setClosed(true);

		messageRepository.save(existingMessage);
		
	}
	
	
	
	
	
	
	
}
