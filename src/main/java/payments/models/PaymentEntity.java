package payments.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "payment_statistic")
@Entity
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @Column(name = "created_dt")
    private LocalDateTime createdTime;

    @JsonProperty
    /*в задачи на поля навешаны ограничения в entity их тоже надо обозначить
    @Column(precision = 12, scale = 2)*/
    private BigDecimal sum;

    @JsonProperty
    private BigDecimal commission;

    @JsonProperty
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private StatusEnum status;

}
