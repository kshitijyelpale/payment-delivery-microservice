package com.wefox.delivery.repositories;

import com.wefox.delivery.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, String> {
}
