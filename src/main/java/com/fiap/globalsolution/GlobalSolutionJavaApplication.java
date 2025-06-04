package com.fiap.globalsolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlobalSolutionJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalSolutionJavaApplication.class, args);
        System.out.println("\n>>> Global Solution Java API iniciada com sucesso! <<<");
        System.out.println("Acesse a documentação da API (Swagger UI) em: http://localhost:8080/swagger-ui.html (após configurar Swagger)\n");
    }

}

