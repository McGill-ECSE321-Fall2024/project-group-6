package ca.mcgill.ecse321.gameshop.service;

import ca.mcgill.ecse321.gameshop.model.Payment;
import ca.mcgill.ecse321.gameshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(String aBillingAddress, int aCreditCardNb, String aExpirationDate, int aCvc, int aTotal) {
        Payment newPayment = new Payment(aBillingAddress, aCreditCardNb, aExpirationDate, aCvc, aTotal);
        return paymentRepository.save(newPayment);
    }

    public Payment updatePayment(int id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        payment.setBillingAddress(payment.getBillingAddress());
        payment.setCreditCardNb(paymentDetails.getCreditCardNb());
        payment.setExpirationDate(paymentDetails.getExpirationDate());
        payment.setCvc(paymentDetails.getCvc());
        payment.setTotal(payment.getTotal());
        return paymentRepository.save(payment);
    }

    public void deletePayment(int paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    public Payment getPaymentById(int paymentId) {
        Payment payment = paymentRepository.findPaymentByPaymentId(paymentId);
        if (payment == null){
            throw new IllegalArgumentException("There is no payment with ID" + paymentId + ".");
        }
        return payment;
    }

    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

}
