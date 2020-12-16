package payments.repository;

import org.springframework.data.jpa.domain.Specification;
import payments.models.PaymentEntity;
import payments.models.StatusEnum;

/*за удаленными зависимостями надо следить*/
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentSpecification {
    public static Specification<PaymentEntity> paymentByStatus(final StatusEnum status) {
        /*без (Specification<PaymentEntity>) тоже будет работат*/
        return (Specification<PaymentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            /*зачем return внутри функции?*/
            return criteriaBuilder.equal(root.get("status"), status.ordinal());
        };
    }

    public static Specification<PaymentEntity> paymentCreateBefore(final LocalDateTime before) {
        return (Specification<PaymentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get("createdTime"), before);
        };
    }

    public static Specification<PaymentEntity> paymentCreateAfter(final LocalDateTime after) {
        return (Specification<PaymentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), after);
        };
    }

    public static Specification<PaymentEntity> trueSpecification () {
        return (Specification<PaymentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.and();
        };
    }
}