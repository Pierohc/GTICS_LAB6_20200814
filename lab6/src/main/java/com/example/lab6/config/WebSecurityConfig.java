package com.example.lab6.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.core.GrantedAuthority;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;

@Configuration
public class WebSecurityConfig {


    final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource){
        this.dataSource = dataSource;

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        //para loguearse sqlAuth -> username | password | enable
        String sqlAuth = "SELECT u.correo,u.psw,u.enable FROM users u where correo = ?";

        //para autenticación -> username, nombre del rol
        String sqlAuto = "SELECT u.correo, r.nombre FROM users u " +
                "inner join roles r on u.rol_id = r.id " +
                "where u.correo = ?";

        users.setUsersByUsernameQuery(sqlAuth);
        users.setAuthoritiesByUsernameQuery(sqlAuto);
        return users;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/processLogin")
                .successHandler((request, response, authentication) -> {

                    DefaultSavedRequest defaultSavedRequest =
                            (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");


                    //si vengo por url -> defaultSR existe
                    if (defaultSavedRequest != null) {
                        String targetURl = defaultSavedRequest.getRequestURL();
                        new DefaultRedirectStrategy().sendRedirect(request, response, targetURl);
                    } else {
                        String rol = "";
                        for (GrantedAuthority role : authentication.getAuthorities()) {
                            rol = role.getAuthority();
                            break;
                        }

                        if (rol.equals("admin")) {
                            response.sendRedirect("/admin");
                        } else {
                            response.sendRedirect("/loginForm");
                        }
                    }
                });



        http.authorizeHttpRequests()
                .requestMatchers("/employee","/employee**").hasAnyAuthority("admin")
                .anyRequest().permitAll();

        return http.build();
    }






}
