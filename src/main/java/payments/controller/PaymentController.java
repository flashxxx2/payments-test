package payments.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import payments.models.PaymentEntity;
import payments.models.StatusEnum;
import payments.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    /*
    Autowired принято заменяеть конструктором
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    но у тебя используется ламбок и надо использовать его
    */
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    /*для RequestBody нельзя использовать entity это очень плохая практика должен быть DTO слой*/
    public void savePayment(@RequestBody PaymentEntity paymentEntity) {
        paymentService.savePayment(paymentEntity);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }

    @GetMapping
    /*так же для отдаваемой модели должен быть слой DTO*/
    public List<PaymentEntity> findAll(@RequestParam(required = false) StatusEnum status,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                               LocalDateTime after,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       @RequestParam(required = false)
                                               LocalDateTime before) {
        return paymentService.findByFilter(status, after, before);
    }
}
