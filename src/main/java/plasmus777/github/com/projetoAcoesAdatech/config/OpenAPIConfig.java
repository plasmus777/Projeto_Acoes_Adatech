package plasmus777.github.com.projetoAcoesAdatech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("")
    private String devUrl;


    @Bean
    public OpenAPI projetoOpenAPI() {
        Server developmentServer = new Server();
        developmentServer.setUrl(devUrl);
        developmentServer.setDescription("URL do servidor em ambiente de desenvolvimento");

        Contact contact = new Contact();
        contact.setName("Fernando Lopes, Jonathan Eduardo de Oliveira e Lucas Souza");
        contact.setUrl("https://github.com/plasmus777/Projeto_Acoes_Adatech");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Projeto Ações Adatech")
                .version("1.0")
                .contact(contact)
                .description("Essa API fornece métodos para que usuários gerenciem seus ativos financeiros.")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(developmentServer));
    }
}