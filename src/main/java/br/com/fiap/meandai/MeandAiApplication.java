package br.com.fiap.meandai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MeandAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeandAiApplication.class, args);
    }

}
