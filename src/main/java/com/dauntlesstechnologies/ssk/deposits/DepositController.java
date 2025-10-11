package com.dauntlesstechnologies.ssk.deposits;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/deposits")
@CrossOrigin
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @GetMapping
    public List<DepositDto> findAll() {
        return depositService.findAllDeposits();
    }

    @GetMapping("/getDeposit/{id}")
    public DepositDto findDepositByFlatNumber(@PathVariable("id") String flatNumber ) {
        return depositService.findDepositByFlatNumber(flatNumber);
    }

    @GetMapping("/depositCount")
    public List<Integer> countDepositsCollectedVsTotal(){
        return depositService.updateApartmentDepositsAndCountCollected();
    }

    @PutMapping("/updateDeposit")
    public void updateDeposit(@RequestBody UpdateDepositDto updateDepositDto) {
        depositService.updateDeposit(updateDepositDto);
    }




    /*
    C - NOT NEEDED, SINCE WILL BE FIXED IN SQL SCRIPTS

    R - 1. Get all DTO record - DONE
        2. Get a DTO record for a flat (dropdown for admin) - DONE
        3. Count how many deposits were paid - DONE

    U - DESIGN CHOICE - ALLOWING ADMIN TO MODIFY THE TOTAL DEPOSIT AND TOTAL NEGOTIATED TO
        AVOID CONFUSION
        Note that in update it should be a summation rather than new dto record !!!! CAREFUL
        ex. someone pays half today and half after joining - edge case that happens, mentioned
        by client!!!!

    D - NOT NEEDED, SINCE WILL BE CLEARED WHEN TENANT LEAVES
     */

}
