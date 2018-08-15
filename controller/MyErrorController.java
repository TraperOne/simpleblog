package myblog.simpleblog.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")
    public String errorLogging(){
        return "errorPage";
    }
    @GetMapping("/errorLogin")
    public String errorLoginLogging() {
        return "errorLoginPage";
    }
        //gdy nie ma mapingu na wskazany adres przechodzimy na wskazany adress

    @Override
    public String getErrorPath(){
        return "/error";
    }
}
