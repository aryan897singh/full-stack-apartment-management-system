package com.dauntlesstechnologies.ssk.lease;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leases")
@CrossOrigin
public class LeaseController {

    private final LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @PostMapping
    public ResponseEntity<Lease> createLease(@RequestBody CreateLeaseDto createLeaseDto) {
        Lease newLease = leaseService.createLease(createLeaseDto);
        return new ResponseEntity<>(newLease, HttpStatus.CREATED);
    }

    @GetMapping("/apartment/{flatNumber}")
    public ResponseEntity<LeaseDto> getCurrentLease(@PathVariable String flatNumber) {
        LeaseDto currentLease = leaseService.getCurrentLeaseFromApartment(flatNumber);
        return ResponseEntity.ok(currentLease);
    }

    @PutMapping("/{leaseId}")
    public ResponseEntity<Void> updateLease(
            @PathVariable Long leaseId,
            @RequestBody UpdateLeaseDto updateLeaseDto) {
        leaseService.updateLease(updateLeaseDto, leaseId);
        return ResponseEntity.ok().build();
    }
}