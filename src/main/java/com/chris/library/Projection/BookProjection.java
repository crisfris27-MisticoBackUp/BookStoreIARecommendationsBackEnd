package com.chris.library.Projection;

import org.springframework.data.rest.core.config.Projection;
import com.chris.library.entities.Book;

@Projection(name = "bookProjection", types = { Book.class })
public interface BookProjection {

	Long getId();

	String getTitle();

	String getAuthor();

	String getDescription();

	int getCopies();

	int getCopiesAvailable();

	String getCategory();

	String getImg();

}
