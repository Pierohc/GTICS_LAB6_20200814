package com.example.lab6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

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
        String sqlAuth = "SELECT email,pwd,id_rol FROM users where email = ?";

        //para autenticación -> username, nombre del rol
        String sqlAuto = "SELECT u.correo, r.nombre FROM users u " +
                "inner join roles r on u.id_rol = r.id " +
                "where u.correo = ?";

        users.setUsersByUsernameQuery(sqlAuth);
        users.setAuthoritiesByUsernameQuery(sqlAuto);
        return users;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/employee","/employee**").hasAnyAuthority("admin")
                .anyRequest().permitAll();

        return http.build();
    }






}
