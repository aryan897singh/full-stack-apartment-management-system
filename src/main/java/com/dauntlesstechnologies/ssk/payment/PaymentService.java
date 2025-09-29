package com.dauntlesstechnologies.ssk.payment;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApartmentRepository  apartmentRepository;
    //injecting the repo layer into the service layer
    public PaymentService(PaymentRepository paymentRepository, ApartmentRepository apartmentRepository) {
        this.paymentRepository = paymentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public PaymentDto findPayment(Long id){
       Optional<Payment> paymentOptional = paymentRepository.findById(id);

       if(paymentOptional.isPresent()){
           Payment payment = paymentOptional.get();
           return entityToDto(payment);
       }
       else{
           throw new RuntimeException("Payment Not Found");
       }

    }

    public List<PaymentDto> findAllPayments(){
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();

            for(Payment payment : payments){
                paymentDtos.add(entityToDto(payment));
            }
            return paymentDtos;
        }

    public void createPayment(UpdatePaymentDto updatePaymentDto){
        Payment payment = new Payment();

        Optional<Apartment> apartmentOptional =  apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());

        if (apartmentOptional.isPresent()){
            Apartment apartment = apartmentOptional.get();
            payment.setApartment(apartment);
        }
        else{
            throw new RuntimeException("Apartment Not Found");
        }

        payment.setAmount(updatePaymentDto.amount());
        payment.setPaymentMethod(updatePaymentDto.paymentMethod());
        payment.setPaymentDate(updatePaymentDto.paymentDate());
        paymentRepository.save(payment);
    }

    public void updatePayment(Long id, UpdatePaymentDto updatePaymentDto){
        Payment payment;

        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()){
            payment = paymentOptional.get();

            Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());

            if (apartmentOptional.isPresent()){
                Apartment apartment = apartmentOptional.get();
                payment.setApartment(apartment);
            }
            else{
                throw new RuntimeException("No such apartment found with provided flat number");
            }

            payment.setAmount(updatePaymentDto.amount());
            payment.setPaymentMethod(updatePaymentDto.paymentMethod());
            payment.setPaymentDate(updatePaymentDto.paymentDate());

            paymentRepository.save(payment);
        }
        else{
            throw new RuntimeException("Payment Not Found");
        }



    }

    public void deletePayment(Long id){

        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if(paymentOptional.isPresent()){
            paymentRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("No such payment record found :(");
        }


    }

    public PaymentDto entityToDto(Payment payment){
        return new PaymentDto(
                payment.getId(),
                payment.getApartment().getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate()
        );

    }





}
