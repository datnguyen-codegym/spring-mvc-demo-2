package codegym.vn.controller;

import codegym.vn.model.Gender;
import codegym.vn.model.UploadFile;
import codegym.vn.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public ModelAndView home() {
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
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("home");
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
