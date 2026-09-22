package codegym.vn.controller;

import codegym.vn.service.HomeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @RequestMapping("/get-home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("hometest");
        modelAndView.addObject("name", homeService.getName());
        return modelAndView;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add() throws Exception {
        throw new Exception("test");
    }

    @GetMapping("/owners/{ownerId}/pets/{petId}")
    public String findPet(@PathVariable Long ownerId, @PathVariable Long petId, ModelMap modelMap) {
        modelMap.addAttribute("ownerId", ownerId);
        modelMap.addAttribute("petId", petId);

        return "hometest";
    }

    @GetMapping("/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
    public String getVersionByRegex(@PathVariable String version,
                                    @PathVariable String name,
                                    @PathVariable String ext) {

        return "hometest";
    }



    @GetMapping("/request-param")
    public String getVersionByRegex(@RequestParam(name = "search-key", defaultValue = "test") String searchKey,
                                    @ModelAttribute User pet) {

        return "hometest";
    }

    @PostMapping(value = "/request-body", consumes =  "application/json")
    @ResponseBody
    public User testRequestBody(@RequestBody User user) {
        return user;
    }
}
