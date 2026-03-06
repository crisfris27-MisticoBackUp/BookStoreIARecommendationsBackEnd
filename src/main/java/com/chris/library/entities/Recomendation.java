package com.chris.library.entities;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="recomendation")
@Data
public class Recomendation {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="user_email")
    private String userEmail;
    
    @Column(name="favorite_genres")
    private String favoriteGenres;
    
    @Column(name="reading_frequency")
    private String readingFrequency;
    
    @Column(name="preferred_length")
    private String preferredLength;
    
    @ElementCollection
    @CollectionTable(name = "recomendation_interests", joinColumns = @JoinColumn(name = "recomendation_id"))
    @Column(name = "interest")
    private List<String> interests;

    
    public Recomendation() {}
    
	public Recomendation(Long id, String userEmail, String favoriteGenres, String readingFrequency,
			String preferredLength, List<String> interests) {
		this.id = id;
		this.userEmail = userEmail;
		this.favoriteGenres = favoriteGenres;
		this.readingFrequency = readingFrequency;
		this.preferredLength = preferredLength;
		this.interests = interests;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFavoriteGenres() {
		return favoriteGenres;
	}

	public void setFavoriteGenres(String favoriteGenres) {
		this.favoriteGenres = favoriteGenres;
	}

	public String getReadingFrequency() {
		return readingFrequency;
	}

	public void setReadingFrequency(String readingFrequency) {
		this.readingFrequency = readingFrequency;
	}

	public String getPreferredLength() {
		return preferredLength;
	}

	public void setPreferredLength(String preferredLength) {
		this.preferredLength = preferredLength;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	@Override
	public String toString() {
		return "Recomendation [id=" + id + ", userEmail=" + userEmail + ", favoriteGenres=" + favoriteGenres
				+ ", readingFrequency=" + readingFrequency +
				", preferredLength=" + preferredLength + ", interests="
				+ interests + "]";
	}
    
    
    
    
    
    

}
