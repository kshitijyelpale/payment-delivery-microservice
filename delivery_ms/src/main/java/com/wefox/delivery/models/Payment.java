package com.wefox.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String payment_id;
    private Long account_id;
    private String payment_type;
    private String credit_card;
    private Integer amount;
}
