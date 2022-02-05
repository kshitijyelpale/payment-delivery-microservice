package com.wefox.delivery.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.delivery.models.Error;
import com.wefox.delivery.models.ErrorType;
import com.wefox.delivery.models.Payment;
import com.wefox.delivery.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class PaymentConsumer {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "online", groupId = "group_id")
    public void consumeOnlinePayment(String message) {
        var payment = getPaymentObject(message);
        assert payment != null;
        paymentService.validateOnlinePayment(payment);
    }

    @KafkaListener(topics = "offline", groupId = "group_id")
    public void consumeOfflinePayment(String message) {
        var payment = getPaymentObject(message);
        paymentService.storePayment(payment);
    }

    private Payment getPaymentObject(String message) {
        try {
            return objectMapper.readValue(message, Payment.class);
        }
        catch(Exception e) {
            var error = new Error(fetchPaymentId(message), ErrorType.other, e.getMessage());
            log.error(error.toString());
            paymentService.sendErrorToLogSystem(error);
        }
        return null;
    }

    private String fetchPaymentId(String message) {
        var pattern = "\"payment_id\"[:]\s(\".*\")";
        var compiledPattern = Pattern.compile(pattern);
        var m = compiledPattern.matcher(message);
        return m.group(0);
    }
}
