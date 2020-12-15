package payments.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import payments.models.PaymentEntity;
import payments.models.StatusEnum;
import payments.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static payments.repository.PaymentSpecification.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private final PaymentRepository paymentRepository;

    public void savePayment(PaymentEntity paymentEntity) {
        paymentRepository.save(paymentEntity);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentEntity> getAll() {
        return paymentRepository.findAll();
    }

    public List<PaymentEntity> findByFilter(StatusEnum status, LocalDateTime after, LocalDateTime before) {
        return paymentRepository.findAll(getPaymentCriteria(status, after, before));
    }

    private Specification<PaymentEntity> getPaymentCriteria(StatusEnum status, LocalDateTime after, LocalDateTime before) {
        Specification<PaymentEntity> where = trueSpecification();
        if (status != null) {
            where = where.and(paymentByStatus(status));
        }
        if (after != null) {
            where = where.and(paymentCreateAfter(after));
        }
        if (before != null) {
            where = where.and(paymentCreateBefore(before));
        }
        return where;
    }
}
