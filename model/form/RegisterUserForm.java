package myblog.simpleblog.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterUserForm {
    @NotBlank(message = "Email nie moze być pusty")
    @Email(message = "Niepoprawny format adresu email")
    private String email;
    @Size(min=6, max=20, message = "Hasło musi mieć od {min} do {max} znaków")
    private String password;
    @NotBlank(message = "Imie nie może być puste")
    private String firstname;
    @NotBlank(message = "Nazwisko nie może być puste")
    private String lastname;

    public RegisterUserForm() { }

    public RegisterUserForm(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
