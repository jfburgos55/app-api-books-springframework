package com.aluracursos.desafio;

import com.aluracursos.desafio.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppBooksSpringBoot implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppBooksSpringBoot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Mostrar el menu
		Principal principal = new Principal();
		principal.muestraMenu();
	}
}
