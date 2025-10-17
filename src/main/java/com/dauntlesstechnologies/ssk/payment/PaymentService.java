package com.dauntlesstechnologies.ssk.payment;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        @Transactional
        public void createPaymentAndUpdateApartmentRecord(UpdatePaymentDto updatePaymentDto){
        Payment payment = new Payment();
        Apartment apartment;

        Optional<Apartment> apartmentOptional =  apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());

        if (apartmentOptional.isPresent()){
            apartment = apartmentOptional.get();
            payment.setApartment(apartment);
        }
        else{
            throw new RuntimeException("Apartment Not Found");
        }

        payment.setRentAmount(updatePaymentDto.rentAmount());
        payment.setMaintenanceAmount(updatePaymentDto.maintenanceAmount());
        payment.setElectricityAmount(updatePaymentDto.electricityAmount());
        payment.setPaymentMethod(updatePaymentDto.paymentMethod());
        payment.setPaymentDate(updatePaymentDto.paymentDate());
        paymentRepository.save(payment);

        //Need to add up the total rent, unlike broken payment records
        //hence we need to add to existing rather than set new payment
        //and the apt record will reset monthly anyway
        apartmentRepository.addPaymentToApartment(
                apartment.getId(),
                updatePaymentDto.rentAmount(),
                updatePaymentDto.maintenanceAmount()
                );


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

            payment.setRentAmount(updatePaymentDto.rentAmount());
            payment.setMaintenanceAmount(updatePaymentDto.maintenanceAmount());
            payment.setElectricityAmount(updatePaymentDto.electricityAmount());
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
                payment.getApartment().getFlatNumber(),
                payment.getRentAmount(),
                payment.getMaintenanceAmount(),
                payment.getElectricityAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate()
        );

    }





}
