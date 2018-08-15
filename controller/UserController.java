package myblog.simpleblog.controller;

import myblog.simpleblog.model.entity.User;
import myblog.simpleblog.model.form.PasswordChangeForm;
import myblog.simpleblog.model.form.RegisterUserForm;
import myblog.simpleblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerUserForm", new RegisterUserForm());
        return "registerForm";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid RegisterUserForm registerUserForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "registerForm";
        }

        //zapis przez klasę z service
        userService.createUser(registerUserForm);
        return "redirect:/";
    }
    @GetMapping("/changePassword")
    public String changePassword(Model model){
        //utworzenie UserPasswordForm
        PasswordChangeForm passwordChangeForm = new PasswordChangeForm();
        model.addAttribute("passwordChangeForm",passwordChangeForm);
        return "changePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute @Valid PasswordChangeForm passwordChangeForm, BindingResult bindingResult, Authentication auth){
        if(bindingResult.hasErrors()){
            return "changePassword";
        }
        UserDetails loogedUser = (UserDetails) auth.getPrincipal();
        //zalogowano na adres Email
        String currentEmail = loogedUser.getUsername();
        //zwróć użytkownika - obiekt user którego zalogowano
        User currentUser = userService.getUser(currentEmail);
        System.out.println("aktualne hasło: "+currentUser.getPassword());
        System.out.println("zmienione hasło: "+passwordChangeForm.getPassword1());
        //update
        userService.changePassword(passwordChangeForm, currentUser.getId());
        return "changePassword";
    }
}
