package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.pgs.ecommerce.Ecommerce.domain.model.DataPayment;
import com.pgs.ecommerce.Ecommerce.domain.model.UrlPaypalResponse;
import com.pgs.ecommerce.Ecommerce.infrastructure.service.PaypalService;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling PayPal payment operations.
 * Provides endpoints to create payments and handle payment statuses.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payments")
@Slf4j
@Hidden  // This hides the entire controller from OpenAPI documentation
public class PaypalController {

    private final PaypalService paypalService;
    private final String SUCCESS_URL = "http://localhost:8085/api/v1/payments/success";
    private final String CANCEL_URL = "http://localhost:8085/api/v1/payments/cancel";
    private final String ERROR_URL = "http://localhost:8085/api/v1/payments/error"; 

    /**
     * Creates a PayPal payment and returns the approval URL.
     *
     * @param dataPayment The payment details received from the frontend.
     * @return The PayPal approval URL or a default URL if an error occurs.
     */
    @PostMapping
    public UrlPaypalResponse createPayment(@RequestBody DataPayment dataPayment) {
        log.info("Received payment request: {}", dataPayment);

        try {
            Payment payment = paypalService.createPayment(
                Double.valueOf(dataPayment.getAmount()), 
                dataPayment.getCurrency(),
                dataPayment.getMethod(),
                "SALE",
                dataPayment.getDescription(),
                CANCEL_URL,
                SUCCESS_URL
            );

            log.info("Payment created successfully: {}", payment.getId());

            String approvalUrl = payment.getLinks().stream()
                    .filter(link -> "approval_url".equals(link.getRel()))
                    .map(link -> link.getHref())
                    .findFirst()
                    .orElse("");

            return new UrlPaypalResponse(approvalUrl);
        } catch (NumberFormatException e) {
            log.error("Invalid amount format: {}", dataPayment.getAmount(), e);
        } catch (PayPalRESTException e) {
            log.error("Error creating PayPal payment", e);
        }

        return new UrlPaypalResponse("http://localhost:4200");
    }
    
    /**
     * Handles successful PayPal payments.
     * Executes the payment and redirects the user based on the payment state.
     *
     * @param paymentId The PayPal payment ID.
     * @param payerId The PayPal payer ID.
     * @return A redirect to the appropriate URL based on payment success or failure.
     */
    @GetMapping("/success")
    public RedirectView paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                return new RedirectView("http://localhost:4200/payment/success");
            } else {
                return new RedirectView(ERROR_URL);
            }
        } catch (PayPalRESTException e) {
            log.error("Error while executing payment :: paymentSuccess ", e);
            return new RedirectView(ERROR_URL);  
        }
    }

    /**
     * Handles canceled PayPal payments.
     * Redirects the user to the home page upon payment cancellation.
     *
     * @return A redirect to the home page.
     */
    @GetMapping("/cancel")
    public RedirectView paymentCancelled() {
        log.info("Payment cancelled");
        return new RedirectView("http://localhost:4200");  
    }

    /**
     * Handles errors during PayPal payments.
     * Redirects the user to the error page upon payment failure.
     *
     * @return A redirect to the error page.
     */
    @GetMapping("/error")
    public RedirectView paymentError() {
        log.info("Payment error");
        return new RedirectView("http://localhost:4200/payment/error");  
    }
}
