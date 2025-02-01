package com.pgs.ecommerce.Ecommerce.infrastructure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class PaypalService {

	private final APIContext apiContext;
	
	public Payment createPayment(
			Double total, 
			String currency, 
			String method, 
			String intent, 
			String description,
			String cancelUrl,
			String successUrl
			
	) throws PayPalRESTException {
		log.info("Starting payment creation: total={}, currency={}, method={}, intent={}", 
                total, currency, method, intent);

		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));
		
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		Payer payer = new Payer();
		payer.setPaymentMethod(method);
		
		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setReturnUrl(successUrl);
		redirectUrls.setCancelUrl(cancelUrl);
		
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        log.info("Executing payment: paymentId={}, payerId={}", paymentId, payerId);

		Payment payment = new Payment();
		payment.setId(paymentId);
		
		PaymentExecution execution = new PaymentExecution();
		execution.setPayerId(payerId);
		return payment.execute(apiContext, execution);
	}
}
