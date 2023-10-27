package com.platzi.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        //INSTANCIANDO LA CLASE
        CorsConfiguration corsConfiguration=new CorsConfiguration();

        //CONFIGURACION PARA PERMITIR LAS DIRECCIONES WEB DISPONIBLES PARA HACER PETICIONES
        corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
        //CONFIGURACION DE LOS METODOS DE PETICION PERMITIDOS
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        //CONFIGURACION DE CABEZERAS PERMITIDAS
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        //
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        //INDICA A SOURCE QUE SE VA A CONGIFURAR LOS CORS PARA  EL PROYECTO(/**) Y TAMBIEN SE INDICA
        //CUAL ES LA CONFIGURACION
        source.registerCorsConfiguration("/**",corsConfiguration);

        return source;
    }
}
