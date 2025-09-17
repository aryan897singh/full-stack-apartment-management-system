package com.dauntlesstechnologies.ssk.apartments;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService){
        this.apartmentService = apartmentService;
    }

    @GetMapping("/{id}")
    public ApartmentDto getApartment(@PathVariable("id") Long id){
        return apartmentService.findApartmentById(id);
    }

    @PutMapping("/update/{id}")
    public void updateApartment(@PathVariable("id") Long id, @RequestBody UpdateApartmentDto updateApartmentDto){
        apartmentService.updateApartmentById(id, updateApartmentDto );
    }

    @PostMapping("/create")
    public void createApartment(@RequestBody UpdateApartmentDto updateApartmentDto){
        apartmentService.createApartment(updateApartmentDto);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteApartment(@PathVariable("id") Long id){
        apartmentService.deleteApartment(id);
    }




    /*
    C - DONE
    R - DONE
    U - DONE
    D
     */

}
