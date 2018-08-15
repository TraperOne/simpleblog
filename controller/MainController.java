package myblog.simpleblog.controller;

import myblog.simpleblog.model.entity.Contact;
import myblog.simpleblog.service.AutoMailingService;
import myblog.simpleblog.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {

    ContactService contactService;
    AutoMailingService autoMailingService;

    @Autowired
    public MainController(ContactService contactService, AutoMailingService autoMailingService) {
        this.contactService = contactService;
        this.autoMailingService = autoMailingService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication auth){
        if(auth != null) {
            UserDetails principal = (UserDetails) auth.getPrincipal();
            model.addAttribute("principal", principal);
        }
            return "homePage";
    }
    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        //powiązanie obiektu klasy Contact z obiektem contact z szblonu html
        model.addAttribute("contact", new Contact());
        return "contactForm";
    }
    @PostMapping("/contact")
    public String contact(@ModelAttribute @Valid Contact contact, BindingResult bindingResult, Model model) {
        String info = "";
        if(bindingResult.hasErrors()) {
            info = "występują błędy formularza";
            model.addAttribute("info", info);
            return "contactForm";
        }
        //zapis do DB poprzez contactService
        contactService.createContact(contact);
        //auto-email
//        autoMailingService.sendSimpleMessage(contact.getEmail(),
//                "Powiadomienie wysłania Formularza kontaktowego.",
//                "Dziękujemy za kontakt. Niezwłocznie się do Ciebie odezwiemy.");
        contact.setSubject("");
        contact.setMessage("");
        contact.setEmail("");
        info = "wysłano wiadomość";
        model.addAttribute("info", info);
        return "contactForm";

    }
}
