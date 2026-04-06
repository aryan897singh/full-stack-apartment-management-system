package com.dauntlesstechnologies.ssk.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") Long id){
        return ResponseEntity.ok(paymentService.findPayment(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments(){
        return ResponseEntity.ok(paymentService.findAllPayments());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody UpdatePaymentDto updatePaymentDto){
        return new ResponseEntity<>(paymentService.createPayment(updatePaymentDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updatePayment(@PathVariable("id") Long id,@RequestBody UpdatePaymentDto updatePaymentDto){
        paymentService.updatePayment(id, updatePaymentDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


}
