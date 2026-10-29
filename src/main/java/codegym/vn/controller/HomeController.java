package codegym.vn.controller;

import codegym.vn.core.BusinessException;
import codegym.vn.model.Gender;
import codegym.vn.model.UploadFile;
import codegym.vn.model.User;
import codegym.vn.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final UserValidator userValidator;

    private final MessageSource messageSource;

//    @InitBinder("user")
//    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.addValidators(userValidator);
//    }

    @RequestMapping(value = "/home")
    public ModelAndView home(@CookieValue(value = "hitCounter", defaultValue = "0") Long hitCounter,
                             HttpServletResponse response, HttpServletRequest request, HttpSession session) {

        System.out.println(session.getId());
        // increment hit counter
        hitCounter++;
//        // create cookie and set it in response
        Cookie cookie = new Cookie("hitCounter", hitCounter.toString()); cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        System.out.println("đã vào màn home");
        ModelAndView modelAndView = new ModelAndView();
//
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("home");
        modelAndView.addObject("allGenders", Gender.values());
        modelAndView.addObject("upload", new UploadFile());


        //get all cookies
        Cookie[] cookies = request.getCookies();
        //iterate each cookie
        modelAndView.addObject("cookieValue", cookie);
        cookie.setPath("/");
        for (Cookie ck : cookies) {
            //display only the cookie with the name 'hitCounter'

            if (ck.getName().equals("hitCounter")) {
                cookie.setPath("/");
                modelAndView.addObject("cookieValue", ck);
                break;
            }
        }
//        modelAndView.addObject("welcome_message", messageSource.getMessage("welcome", new String[]{"Nguyen Tien Dat"}, Locale.ENGLISH));

//        ResourceBundle.getBundle("message_vi").getString("welcome");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionResolver(Exception ex, HttpServletRequest request) {

        String servletPath = request.getServletPath();

        String view = "error";

        if (null != servletPath) {
            view = servletPath.replace("/", "");
        }

        ModelAndView modelAndView = new ModelAndView(view);

        if (ex instanceof BusinessException) {
            modelAndView.addObject("ex", ex);
            modelAndView.addObject("user", new User());
            modelAndView.addObject("upload", new UploadFile());
        }

        return modelAndView;
    }

    @PostMapping(
            value = "/create-user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<?> createUser(@Validated @ModelAttribute("user") User user        ,
                                     BindingResult bindingResult,
                                     ModelAndView modelAndView,
                                     HttpSession session) {
//        System.out.println("vào màn create user");
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("home");
//            modelAndView.addObject(user);
//            return modelAndView;
//        }
        Map<String, String> result = new HashMap<>();
        result.put("message", "Tạo user thành công");
        result.put("username", user.getUsername());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public String uploadFile(@ModelAttribute("upload") UploadFile uploadfile) throws IOException {
        FileCopyUtils.copy(uploadfile.getFile().getBytes(), new File("/Users/trinhhuong/Desktop/test.png" + uploadfile.getFile().getName()));
        return "upload successfully";
    }

    @GetMapping("/get-message")
    @ResponseBody
    public String getMessage(Locale locale,HttpServletRequest request) {

        HttpSession session = request.getSession();

        System.out.println("Session ID: " + session.getId());
        System.out.println(locale.toLanguageTag());
        return messageSource.getMessage("welcome", null, locale);
    }
}
