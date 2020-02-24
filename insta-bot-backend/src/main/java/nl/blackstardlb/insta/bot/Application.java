package nl.blackstardlb.insta.bot;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
        info = @Info(
                title = "Instabot",
                version = "0.0",
                description = "Instabot API",
                contact = @Contact(url = "https://blackstardlb.nl", name = "Daron", email = "brisondl@gmail.com")
        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}
