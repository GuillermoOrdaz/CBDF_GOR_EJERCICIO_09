package com.upiiz.securityInMemory.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// Establecer la configuración con anotaciones de nuestra api
@EnableMethodSecurity
public class SecurityConfig {
    // SECURITY FILTER CHAIN - CAdena de filtros de seguridad
    // Singleton - TNER solo una instancia
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // Configurar los filtros personalizados
        return httpSecurity
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(http-> {
            http.requestMatchers(HttpMethod.GET, "/api/v2/listar").hasAuthority("READ");
            http.requestMatchers(HttpMethod.GET, "/api/v2/actualizar").hasAuthority("UPDATE");
            http.requestMatchers(HttpMethod.GET, "/api/v2/eliminar").hasAuthority("DELETE");
            http.requestMatchers(HttpMethod.GET, "/api/v2/crear").hasAuthority("CREATE");
        }).build();
    }

    // Authentication Manager - Lo vamos a obtener de una instancia que ya existe
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Authentication Provider - DAO
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // UserDataService - En DB o en Memoria
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails usuarioMiguel = User.withUsername("Guillermo").password("memo1234").roles("ADMIN").authorities("READ", "CREATE", "UPDATE", "DELETE").build(); 
        UserDetails usuarioMarlin = User.withUsername("Marlin").password("marlin1234").roles("USER").authorities("READ", "UPDATE").build();
        UserDetails usuarioInvitado = User.withUsername("Invitado").password("guest").roles("GUEST").authorities("READ").build();
        
        List<UserDetails> userDetailList = new ArrayList<UserDetails>();
        userDetailList.add(usuarioMiguel);
        userDetailList.add(usuarioMarlin);
        userDetailList.add(usuarioInvitado);
        return new InMemoryUserDetailsManager(userDetailList);
    }
}
