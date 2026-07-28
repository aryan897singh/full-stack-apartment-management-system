package com.dauntlesstechnologies.ssk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController - REST CONTROLLERS ARE PURE DATA API ENDPOINTS AND ONLY RETURN DATA AND NOT PAGES!!
@Controller //NOTE: this is a traditional web server controller and uses ThymeLeaf on returning a string
@CrossOrigin
public class GlobalErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied.html"; //This tells ThymeLeaf to look for access-denied.html when this
        //API endpoint is called
    }

    @GetMapping("/")
    public String redirectToLandingPage(){
        return "redirect:/OWNER_PAGES/Dashboard.html";
    } //Currently the landing page is the Owner's dashboard since the MVP is only for the owner

}
