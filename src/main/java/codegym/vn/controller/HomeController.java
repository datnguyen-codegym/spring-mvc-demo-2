package codegym.vn.controller;

import codegym.vn.model.Gender;
import codegym.vn.model.UploadFile;
import codegym.vn.model.User;
import codegym.vn.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final UserValidator userValidator;

    @InitBinder("user")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(userValidator);
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        System.out.println("đã vào màn home");
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("user", new User());
        modelAndView.setViewName("home");
        modelAndView.addObject("allGenders", Gender.values());
        modelAndView.addObject("upload", new UploadFile());
        return modelAndView;
    }

    @PostMapping("/create-user")
//    @ResponseBody
    public ModelAndView createUser(@Validated @ModelAttribute("user") User user        ,
                             BindingResult bindingResult,
                             ModelAndView modelAndView) {
        System.out.println("vào màn create user");
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("home");
            modelAndView.addObject(user);
            return modelAndView;
        }

        modelAndView.setViewName("result");
        return modelAndView;
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public String uploadFile(@ModelAttribute("upload") UploadFile uploadfile) throws IOException {
        FileCopyUtils.copy(uploadfile.getFile().getBytes(), new File("/Users/trinhhuong/Desktop/test.png" + uploadfile.getFile().getName()));
        return "upload successfully";
    }
}
