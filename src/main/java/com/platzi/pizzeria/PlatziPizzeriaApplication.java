package com.platzi.pizzeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories//DE ESTA MANERA LE INDICAMOS A LA APLICACION SPRING QUE TENEMOR REPOSITORIOS DE SPRING
@EnableJpaAuditing
public class PlatziPizzeriaApplication {


	
	//CLASE PRINCIPAL
	public static void main(String[] args) {
		SpringApplication.run(PlatziPizzeriaApplication.class, args);
	}

}
