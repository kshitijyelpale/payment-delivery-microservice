package com.wefox.delivery.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.delivery.models.Error;
import com.wefox.delivery.models.Payment;
import com.wefox.delivery.repositories.AccountRepository;
import com.wefox.delivery.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class OnlinePaymentService {

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
        HttpEntity<String> request = new HttpEntity<>(payment.toString(), httpHeaders);

        restTemplate.postForObject(this.apiSystemUri + "/payment", request, String.class);
        //restTemplate.pos
    }

    @Transactional
    @Async
    public void storePayment(Payment payment) {
        payment = paymentRepository.save(payment);
        var paymentDate = payment.getCreated_on();

        var account = payment.getAccount();
        //accountRepository.


    }

    @Async
    public void sendErrorToLogSystem(Error error) {
        HttpEntity<String> request = new HttpEntity<>(error.toString(), httpHeaders);

        restTemplate.postForObject(this.apiSystemUri + "/log", request, String.class);
    }
}
