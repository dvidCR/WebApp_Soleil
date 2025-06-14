package com.soleil.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.soleil.api.service.EmpleadoService;
import com.soleil.api.service.EmpleadoUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public EmpleadoUserDetailsService userDetailsService(EmpleadoService empleadoService) {
        return new EmpleadoUserDetailsService(empleadoService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, EmpleadoUserDetailsService userDetailsService, CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
          .csrf().disable()
          .authenticationProvider(authenticationProvider(userDetailsService))
          .authorizeHttpRequests(auth -> auth
        		    .requestMatchers("/login", "/", "/anadirServicio", "/img/**", "/css/**", "/js/**").permitAll()
        		    .requestMatchers("/admin/**", 
        		                     "/configurarPacientes/**",
        		                     "/configurarTratamientos/**",
        		                     "/configurarUsuarios/**",
        		                     "/contabilidad/**",
        		                     "/gestionEmpleado/**").hasRole("ADMIN")
        		    .requestMatchers("/portalEmpleado/**").hasAnyRole("EMPLEADO", "ADMIN")
        		    .anyRequest().authenticated()
		  )
          .formLogin(form -> form
              .loginPage("/")
              .loginProcessingUrl("/login")
              .usernameParameter("usuario")
              .passwordParameter("contrasena") 
              .successHandler(successHandler)
              .failureUrl("/login?error")
              .permitAll()
          )
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/login?logout")
              .invalidateHttpSession(true)
              .deleteCookies("JSESSIONID")
          )
          .userDetailsService(userDetailsService)
          .sessionManagement(sess -> sess
              .maximumSessions(1)
              .expiredUrl("/login?expired")
          );

        return http.build();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, EmpleadoUserDetailsService userDetailsService) 
            throws Exception {
        return http
          .getSharedObject(AuthenticationManagerBuilder.class)
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder)
          .and()
          .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}