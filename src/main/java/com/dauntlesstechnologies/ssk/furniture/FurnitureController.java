package com.dauntlesstechnologies.ssk.furniture;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/furniture")
@CrossOrigin
public class FurnitureController {

    private FurnitureService furnitureService;

    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping("/{id}")
    public FurnitureDto getFurnitureById(@PathVariable Long id) {
        return furnitureService.getFurniture(id);
    }

    @GetMapping("/getAll")
    public List<FurnitureDto> getAllFurniture() {
        return furnitureService.getAllFurniture();
    }

    @PostMapping("/create")
    public void createFurniture(@RequestBody UpdateFurnitureDto updateFurnitureDto){
        furnitureService.createFurniture(updateFurnitureDto);
    }

    @PutMapping("/update/{id}")
    public void updateFurniture(@PathVariable("id") Long id, @RequestBody UpdateFurnitureDto updateFurnitureDto){
        furnitureService.updateFurniture(id, updateFurnitureDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFurniture(@PathVariable("id") Long id){
        furnitureService.deleteFurniture(id);
    }




    /*
    C - DONE
    R - Single: DONE  - All: DONE
    U - DONE
    D - DONE
     */
}
