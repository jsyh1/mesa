package mesaconsola.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "mesaconsola")
public class MesaApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MesaApiApplication.class, args);
    }
}
