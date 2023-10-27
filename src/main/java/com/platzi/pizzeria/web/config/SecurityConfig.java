package com.platzi.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration//ANOTACION DE SPRING QUE LE INDICA QUE SERA PARA HACER CONFIGURACIONES, SE CONVERTIRA EN UN BEAN QUE
//SPRING PUEDA AUTOGESTIONAR E INYECTAR DENTRO DE LA APP
public class SecurityConfig {

    @Bean//SE DECLARA COMO BEAN, DEBE DEFINIRSE EN UNA CLASE @CONFIGURATION
    //INDICA A SPRING QUE ESTE METODO ES UNO QUE DEVUELVE UNA INSTANCIA QUE QUEREMOS QUE GUARDE EN SU CONTEXTO
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)//DESABILITA LA PROTECCION CSRF
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(customizeRequests->{//AUTORIZA LAS PETICIONES HTTP
                    customizeRequests
                            //PERMITE POR EL METODO GET ACCEDER A PIZZAS
                            //PERMITIMOS QUE SE HAGAN PETICIONES Y ACEPTE TODAS POR EL METODO GET A LA URL
                            //EN ES TE CASO LOS PERMISOS VAN A PODER HACER ADMIN Y CUSTOMER
                            .requestMatchers(HttpMethod.GET,"/api/pizzas/**").hasAnyRole("ADMIN","CUSTOMER")
                            //PARA PODER HACER POST EN CUALQUIER COSA QUE TENGA EL ENLACE VA A TENER QUE SER ADMIN
                            .requestMatchers(HttpMethod.POST,"/api/pizzas/**").hasRole("ADMIN")
                            //PARA TODOS LAS URLS DE ENPOINTS QUE DESEAN HACER PUT SOLO PODRAN SER CONSUMIDAS SI SON ADMIN
                            .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                            //SOLO LOS ADMIN PODRAN HACER TODAS LAS ACCIONES EN ORDERS
                            .requestMatchers("/api/orders/**").hasRole("ADMIN")
                            .anyRequest()//TODAS LAS PETICIONES
                            .authenticated();//AUTENTICADAS

                })

                .httpBasic(Customizer.withDefaults());//ENCRYPTADAS CON HTTPBASIC


        return http.build();//RETORNA LA CONFIGURACION REALIZADA
    }

    //LO USAMS PARA INDICARLE A SPRING QUE DEJAREMOS DE USAR LOS USUARIOS POR DEFECTO
    //Y QUE LE PONDREMOS NUESTROS USUARIOS PROPIOS
    /*@Bean
    public UserDetailsService memoryUsers(){
        UserDetails admin= User.builder()
                .username("admin")
                //ENCRIPTA LA CONTRASEÑA Y LA PASA POR EL ENCODER
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();


        //ROLES DE USUSARIO
        UserDetails customer=User.builder()
                .username("customer")
                .password(passwordEncoder().encode("customer123"))
                .roles("CUSTOMER")
                .build();
        return new InMemoryUserDetailsManager(admin,customer);
    }

     */

    //ENCODER OBLIGATORIO POR EL CUAL TIENE QUE PASAR LA CONTRASEÑA
    @Bean
    public PasswordEncoder passwordEncoder(){
        //METODO DE ENCODER
        return new BCryptPasswordEncoder();
    }
}
