package com.wefox.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {

    @Id
    private String payment_id;
    private Long account_id;
    private PaymentType payment_type;
    private String credit_card;
    private Integer amount;
    private Timestamp created_on = new Timestamp(System.currentTimeMillis());

    /*@ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonProperty(value = "account_id")
    private Account account;*/
}

