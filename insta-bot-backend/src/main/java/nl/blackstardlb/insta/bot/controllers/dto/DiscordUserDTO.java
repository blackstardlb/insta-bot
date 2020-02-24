package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.http.hateoas.AbstractResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.URI;

@EqualsAndHashCode(callSuper = false)
@Data
public class DiscordUserDTO extends AbstractResource<DiscordUserDTO> {
    private String name;
    private Long id;
    private String avatar;
    private String discriminator;
    private URI avatarURL;
}
