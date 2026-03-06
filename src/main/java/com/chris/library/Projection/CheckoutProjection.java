package com.chris.library.Projection;

import org.springframework.data.rest.core.config.Projection;

import com.chris.library.entities.Checkout;


@Projection(name = "checkoutProjection", types = { Checkout.class })
public interface CheckoutProjection {
	Long getId();
	String getUserEmail();
	String getcheckoutDate();
	String getreturnDate();
	Long getBookId();

}
