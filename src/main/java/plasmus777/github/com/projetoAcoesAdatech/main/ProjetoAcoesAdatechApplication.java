package plasmus777.github.com.projetoAcoesAdatech.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjetoAcoesAdatechApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoAcoesAdatechApplication.class, args);
    }
}
