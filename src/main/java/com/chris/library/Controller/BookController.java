package com.chris.library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.library.entities.Book;
import com.chris.library.responseModels.ShelfCurrentLoansResponse;
import com.chris.library.service.BookService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService=bookService;
	}
	
	@GetMapping("/secure/currentLoans")
	public List<ShelfCurrentLoansResponse>SecurecurrentLoans(@RequestParam String userEmail){
		
		List<ShelfCurrentLoansResponse> theList = null;
		try {
			theList = bookService.currentLoans(userEmail);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return theList;
	}
	
	
	
	@PutMapping("/secure/checkout")
	public Book checkoutBook(@RequestParam String userEmail,@RequestParam Long bookId) {
		
		Book myBook =null;

		try {
			myBook = bookService.checkoutBook(userEmail, bookId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return myBook;
	}
	
	
	@GetMapping("/secure/ischeckout/byUser")
	public boolean checkoutByUser(@RequestParam String userEmail,@RequestParam Long bookId) {
		boolean myAnswer = false;
		try {
		myAnswer = bookService.checkoutBookByUser(userEmail, bookId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return myAnswer;
	}
	
	
	@GetMapping("/secure/currentLoans/count")
	public int currentLoans(@RequestParam String userEmail) {
		int myAnswer = 0;
		try {
			myAnswer = bookService.currentLoanCount(userEmail);		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return myAnswer;
	}
	
	
	@PutMapping("/secure/return")
	public void returnBook(@RequestParam String userEmail,
			@RequestParam Long bookId) throws Exception {
		bookService.returnBook(userEmail, bookId);
		
	}
	
	
	
}
