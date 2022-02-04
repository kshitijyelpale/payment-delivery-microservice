package com.wefox.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    private String payment_id;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    private PaymentType payment_type;
    private String credit_card;
    private Integer amount;
    private Timestamp created_on = new Timestamp(System.currentTimeMillis());
}

