package payments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import payments.models.PaymentEntity;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {

    @Query(value = "SELECT * FROM payment_statistic ORDER BY created_dt", nativeQuery = true)
    List<PaymentEntity> findAll();
}
