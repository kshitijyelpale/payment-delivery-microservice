package com.wefox.delivery.services;

import com.wefox.delivery.models.Error;
import com.wefox.delivery.models.ErrorType;
import com.wefox.delivery.models.Payment;
import com.wefox.delivery.repositories.AccountRepository;
import com.wefox.delivery.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.SQLException;

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

    @Async
    public void validateOnlinePayment(Payment payment) {
        HttpEntity<String> request = new HttpEntity<>(payment.toString(), httpHeaders);

        try {
            var response = restTemplate.postForObject(this.apiSystemUri + "/payment", request, ResponseEntity.class);
            assert response != null;
            if (HttpStatus.valueOf(response.getStatusCodeValue()).is2xxSuccessful()) {
                this.storePayment(payment);
            }
            else {
                var error = new Error(payment.getPayment_id(), ErrorType.network, response.toString());
                log.error(error.toString());
                sendErrorToLogSystem(error);
            }
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
        HttpEntity<String> request = new HttpEntity<>(error.toString(), httpHeaders);

        restTemplate.postForObject(this.apiSystemUri + "/log", request, String.class);
    }
}
