package com.dauntlesstechnologies.ssk.payment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/payments")
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/{id}")
    public PaymentDto getPaymentById(@PathVariable("id") Long id){
        return paymentService.findPayment(id);
    }

    @GetMapping(path = "/getAllPayments")
    public List<PaymentDto> getAllPayments(){
        return paymentService.findAllPayments();
    }

    //IMP NOTE: I want the admin to enter the Apartment Number AND NOT THE Apt ID since
    //the admin DOES NOT KNOW THE apt id, so I AM SUPPOSED TO TAKE THE Number and fetch the ID!!!
    @PostMapping(path = "/createPayment")
    public void createPayment(@RequestBody UpdatePaymentDto updatePaymentDto){
         paymentService.createPayment(updatePaymentDto);
    }

    @PutMapping(path = "/updatePayment/{id}")
    public void updatePayment(@PathVariable("id") Long id,@RequestBody UpdatePaymentDto updatePaymentDto){
        paymentService.updatePayment(id, updatePaymentDto);
    }

    @DeleteMapping(path = "/deletePayment/{id}")
    public void deletePayment(@PathVariable("id") Long id){
        paymentService.deletePayment(id);
    }



    /*
    C - DONE
    R - single - DONE    All - DONE
    U - DONE
    D - DONE
     */

}
