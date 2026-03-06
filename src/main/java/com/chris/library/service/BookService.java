package com.chris.library.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.chris.library.dao.BookRepository;
import com.chris.library.dao.CheckoutRepository;
import com.chris.library.dao.HistoryRepository;
import com.chris.library.entities.Book;
import com.chris.library.entities.Checkout;
import com.chris.library.entities.History;
import com.chris.library.responseModels.ShelfCurrentLoansResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookService {

	private BookRepository bookRepository;
	private CheckoutRepository checkoutRepository;
	private HistoryRepository historyRepository;
	
	public BookService(BookRepository bookRepository, 
			CheckoutRepository checkoutrepository, HistoryRepository historyRepository) {
		this.bookRepository=bookRepository;
		this.checkoutRepository=checkoutrepository;
		this.historyRepository=historyRepository;
	}
	
	public Book checkoutBook(String userEmail, Long bookId)throws Exception{
		Optional<Book>book = bookRepository.findById(bookId);
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

		if(!book.isPresent() || validateCheckout!=null || book.get().getCopiesAvailable()<=0) {
			throw new Exception("Book. does not exist already or it has not been checked by the user");
		}
		book.get().setCopiesAvailable(book.get().getCopiesAvailable()-1);

		bookRepository.save(book.get());
		Checkout checkout = new Checkout(
				userEmail,LocalDate.now().toString(),
				LocalDate.now().plusDays(7).toString(),
				book.get().getId()
				);
		checkoutRepository.save(checkout);
		return book.get();
	}
	
	
	public boolean checkoutBookByUser(String userEmail, Long bookId) {
		Checkout validateCheckout= null;
		try {
		validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);			
		System.out.println("Found checkout: " + validateCheckout);
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		if(validateCheckout != null) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public int currentLoanCount(String userEmail) {
		int sizeOfChecheckout =0;
		try{
			sizeOfChecheckout = checkoutRepository.findBooksByUserEmail(userEmail).size(); 	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sizeOfChecheckout;
	}
	
	
	public List<ShelfCurrentLoansResponse> currentLoans(String userEmail)throws Exception{
		List<ShelfCurrentLoansResponse>shelfCurrentLoansResponse = new ArrayList<>();
		List<Checkout>checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
		System.out.println("here comes the first checkot id list");
		System.out.println(checkoutList);
		List<Long>bookIdList = new ArrayList<>();
		for(Checkout i: checkoutList) {
			bookIdList.add(i.getBookId());
			System.out.println("here comes the checkout Id list");
			System.out.println(bookIdList);
		}
		List<Book>books = bookRepository.findBookByIdBooks(bookIdList);
		System.out.println("new the list of books");
		System.out.println(books);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Book book: books) {
			Optional<Checkout>checkout = checkoutList.stream()
					.filter(x -> x.getBookId() == book.getId()).findFirst();
			
			if(checkout.isPresent()) {
				Date d1 = sdf.parse(checkout.get().getReturnDate());
				Date d2 = sdf.parse(LocalDate.now().toString());

				TimeUnit time = TimeUnit.DAYS;
				long difference_In_Time = time.convert(d1.getTime()-d2.getTime(),
						TimeUnit.MILLISECONDS);
				shelfCurrentLoansResponse.add(new ShelfCurrentLoansResponse(book, 
						(int)difference_In_Time));
			}
		}
		return shelfCurrentLoansResponse;
		
	}
	
	
	
	public void returnBook(String UserEmail, Long bookId)throws Exception {
		Optional<Book>myBook = bookRepository.findById(bookId);
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(UserEmail, bookId);
		if(!myBook.isPresent()||validateCheckout==null) {
			throw new Exception("book does not exists");
		}
		
		myBook.get().setCopiesAvailable(myBook.get().getCopiesAvailable() + 1);
		bookRepository.save(myBook.get());
		
		checkoutRepository.deleteById(validateCheckout.getId());
		
		History history = new History(
				UserEmail, validateCheckout.getCheckoutDate(),
				LocalDate.now().toString(), myBook.get().getTitle(),
				myBook.get().getAuthor(), myBook.get().getDescription(),
				myBook.get().getImg());
		

		historyRepository.save(history);
			
	}
	
	
	public void renewLoan (String useremail, Long Bookid)throws Exception {
		
		Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(useremail, Bookid);
		if(validateCheckout==null) {
			throw new Exception("Book does not exists or not exists or not checked by the user");
		}
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = myFormat.parse(validateCheckout.getReturnDate());
		Date d2 = myFormat.parse(LocalDate.now().toString());
		if(d1.compareTo(d2)>0 || d1.compareTo(d2)==0) {
			validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
			checkoutRepository.save(validateCheckout);
		}
		
	}
	
	
	
	
	
	
	
}
