package com.wefox.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private Long account_id;
    private String name;
    private String email;
    private Date birthdate;
    private Timestamp last_payment_date;
    private Timestamp created_on;

}
