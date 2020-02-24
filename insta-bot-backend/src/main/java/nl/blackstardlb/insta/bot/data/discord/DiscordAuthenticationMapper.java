package nl.blackstardlb.insta.bot.data.discord;

import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jsr330", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DiscordAuthenticationMapper {
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "name", source = "username")
    DiscordAuthentication toDiscordAuthentication(DiscordUser discordUser);
}
