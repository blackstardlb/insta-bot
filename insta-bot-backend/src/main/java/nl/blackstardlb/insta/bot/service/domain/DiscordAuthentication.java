package nl.blackstardlb.insta.bot.service.domain;

import io.micronaut.security.authentication.Authentication;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class DiscordAuthentication implements Authentication {
    private String name;
    private String token;
    private Long id;
    private String avatar;
    private String discriminator;

    @Nonnull
    @Override
    public Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }
}
