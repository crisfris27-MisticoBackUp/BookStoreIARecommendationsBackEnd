package com.chris.library.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Messages")
@Data
public class Messages {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="id")
	private Long id;
	
	public Messages() {}
	
	@Column(name="title")
	private String title;
	
	@Column(name="user_email")
	private String userEmail;

	@Column(name="question")
	private String question;
	
	@Column(name="admin_email")
	private String adminEmail;
	
	@Column(name="response")
	private String response;
	
	@Column(name="closed")
	private boolean closed;
			
	public Messages(String title, String userEmail) {
		this.title = title;
		this.userEmail = userEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "Messages [id=" + id + ", title=" + title + ", userEmail=" + userEmail + ", question=" + question
				+ ", adminEmail=" + adminEmail + ", response=" + response + ", closed=" + closed + "]";
	}

	

}
