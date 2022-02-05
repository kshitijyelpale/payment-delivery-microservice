package com.wefox.delivery.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.delivery.models.Error;
import com.wefox.delivery.models.ErrorType;
import com.wefox.delivery.models.Payment;
import com.wefox.delivery.repositories.AccountRepository;
import com.wefox.delivery.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@Slf4j
public class PaymentService {

    private String apiSystemUri = "http://localhost:9000";
    // private final String apiSystemUri = "http://api-producer:9000";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private ObjectMapper objectMapper;

    @Async
    public void validateOnlinePayment(Payment payment) {
        try {
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payment), httpHeaders);
            var response = restTemplate.exchange(this.apiSystemUri + "/payment", HttpMethod.POST, request, ResponseEntity.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                this.storePayment(payment);
            }
            else {
                var error = new Error(payment.getPayment_id(), ErrorType.network, response.toString());
                log.error(error.toString());
                sendErrorToLogSystem(error);
            }
        }
        catch(JsonProcessingException e) {
            var error = new Error(payment.getPayment_id(), ErrorType.other, e.getMessage());
            log.error(error.toString());
            sendErrorToLogSystem(error);
        }
        catch (Exception e) {
            var error = new Error(payment.getPayment_id(), ErrorType.network, e.getMessage());
            log.error(error.toString());
            sendErrorToLogSystem(error);
        }
    }

    @Transactional
    @Async
    public void storePayment(Payment payment) {
        try {
            payment = paymentRepository.save(payment);
            var paymentDate = payment.getCreated_on();

            var accountId = payment.getAccount_id();
            var account = accountRepository.findById(accountId);
            if (account.isPresent()) {
                account.get().setLast_payment_date(paymentDate);
                accountRepository.save(account.get());
            }
        }
        catch (Exception e) {
            var error = new Error(payment.getPayment_id(), ErrorType.database, e.getMessage());
            log.error(error.toString());
            sendErrorToLogSystem(error);
        }
    }

    @Async
    public void sendErrorToLogSystem(Error error) {
        try {
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(error), httpHeaders);

            restTemplate.exchange(this.apiSystemUri + "/log", HttpMethod.POST, request, String.class);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
