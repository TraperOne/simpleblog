package myblog.simpleblog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/post/delete").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/post/add").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/post/edit").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/post/all").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/changePassword").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .antMatchers("/post/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .defaultSuccessUrl("/")
                .failureUrl("/errorLogin")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().usersByUsernameQuery("SELECT u.email, u.password, u.active FROM user u WHERE u.email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name FROM user u " +
                        "JOIN user_role ur ON ur.user_id = u.id " +
                        "JOIN role r ON ur.roles_id = r.id " +
                        "WHERE u.email = ?").dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }
}
