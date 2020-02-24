package nl.blackstardlb.insta.bot.controllers.mappers;

import nl.blackstardlb.insta.bot.controllers.dto.AddLinks;
import nl.blackstardlb.insta.bot.controllers.dto.DiscordUserDTO;
import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.net.URI;

@Mapper(componentModel = "jsr330", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DiscordUserMapper {
    default URI avatarURL(DiscordAuthentication discordAuthentication) {
        if (discordAuthentication.getAvatar() != null) {
            return URI.create(String.format(
                    "https://cdn.discordapp.com/avatars/%s/%s.png",
                    discordAuthentication.getId(),
                    discordAuthentication.getAvatar()
            ));
        } else {
            long defaultAvatar = Long.parseLong(discordAuthentication.getDiscriminator()) % 5;
            return URI.create(String.format(
                    "https://cdn.discordapp.com/embed/avatars/%s.png",
                    defaultAvatar
            ));
        }
    }

    @AddLinks
    @Mapping(target = "avatarURL", expression = "java(avatarURL(discordAuthentication))")
    DiscordUserDTO toDiscordUserDTO(DiscordAuthentication discordAuthentication);
}
