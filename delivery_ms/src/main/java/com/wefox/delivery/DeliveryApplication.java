package com.wefox.delivery;

import com.wefox.delivery.models.Account;
import com.wefox.delivery.models.Payment;
import com.wefox.delivery.models.PaymentType;
import com.wefox.delivery.repositories.AccountRepository;
import com.wefox.delivery.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableAsync
public class DeliveryApplication {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

    @GetMapping("/test")
    public void test() {
        Long id = 1L;
        var account = accountRepository.findById(id);
        System.out.println(account.get().getName() + "  " + account.get().getCreated_on());

        Payment payment= new Payment();
        payment.setPayment_id("bgf");
        payment.setAccount(account.get());
        payment.setPayment_type(PaymentType.online);
        payment.setCredit_card("ccd");
        payment.setAmount(45);
        var newPay = paymentRepository.save(payment);
        System.out.println(newPay.getPayment_id() + " " + newPay.getCreated_on());

    }

}
