package ca.mcgill.ecse321.gameshop.repository;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gameshop.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    public Payment findPaymentByPaymentId(int paymentId);
}

