package com.dauntlesstechnologies.ssk.payment;

import com.dauntlesstechnologies.ssk.apartments.Apartment;
import com.dauntlesstechnologies.ssk.apartments.ApartmentRepository;
import com.dauntlesstechnologies.ssk.apartments.UpdateApartmentDto;
import com.dauntlesstechnologies.ssk.lease.Lease;
import com.dauntlesstechnologies.ssk.lease.LeaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApartmentRepository apartmentRepository;
    private final LeaseRepository leaseRepository;

    public PaymentService(PaymentRepository paymentRepository, ApartmentRepository apartmentRepository, LeaseRepository leaseRepository) {
        this.paymentRepository = paymentRepository;
        this.apartmentRepository = apartmentRepository;
        this.leaseRepository = leaseRepository;
    }

    public PaymentDto findPayment(Long id){
       Optional<Payment> paymentOptional = paymentRepository.findById(id);

       if(paymentOptional.isPresent()){
           Payment payment = paymentOptional.get();
           return entityToDto(payment);
       }
       else throw new RuntimeException("Payment Not Found");


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
    public PaymentDto createPayment(UpdatePaymentDto updatePaymentDto){
        Payment payment = new Payment();
        Apartment apartment = new Apartment();

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());
        if(apartmentOptional.isPresent()){
            apartment = apartmentOptional.get();
        }else{
            throw new RuntimeException("APARTMENT WITH FLAT NUMBER " + updatePaymentDto.flatNumber() + " NOT FOUND");
        }
        //Now we need to find the associated active lease with that apartment
        Optional<Lease> leaseOptional = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());

        if(leaseOptional.isPresent()){payment.setLease(leaseOptional.get());} else throw new RuntimeException("LEASE NOT FOUND");

        payment.setPaymentType(updatePaymentDto.paymentType());
        payment.setPaymentAmount(updatePaymentDto.paymentAmount());
        payment.setPaymentMethod(updatePaymentDto.paymentMethod());
        payment.setComment(updatePaymentDto.comment());
        payment.setPaymentDate(updatePaymentDto.paymentDate());

        paymentRepository.save(payment);
        return entityToDto(payment);
        }

    public void updatePayment(Long id, UpdatePaymentDto updatePaymentDto){
        Payment payment;
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if(paymentOptional.isPresent()){payment = paymentOptional.get();} else throw new RuntimeException("Payment Record Not Found");

        //In the scenario the owner input the wrong flat number:
        Apartment apartment = new Apartment();

        Optional<Apartment> apartmentOptional = apartmentRepository.findByFlatNumber(updatePaymentDto.flatNumber());
        if(apartmentOptional.isPresent()){
            apartment = apartmentOptional.get();
        }else throw new RuntimeException("APARTMENT WITH FLAT NUMBER " + updatePaymentDto.flatNumber() + " NOT FOUND");

        //Now we need to find the associated active lease with that apartment
        Optional<Lease> leaseOptional = leaseRepository.findByApartmentIdAndIsActiveTrue(apartment.getId());
        payment.setLease(leaseOptional.get());
        payment.setPaymentType(updatePaymentDto.paymentType());
        payment.setPaymentAmount(updatePaymentDto.paymentAmount());
        payment.setPaymentMethod(updatePaymentDto.paymentMethod());
        payment.setComment(updatePaymentDto.comment());
        payment.setPaymentDate(updatePaymentDto.paymentDate());

        paymentRepository.save(payment);

    }

    //In the scenario the owner input the wrong payment
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
                payment.getLease().getApartment().getFlatNumber(),
                payment.getPaymentType(),
                payment.getPaymentAmount(),
                payment.getPaymentMethod(),
                payment.getComment(),
                payment.getPaymentDate()
        );

    }





}
