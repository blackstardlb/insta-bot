package nl.blackstardlb.insta.bot.service.domain;

import nl.blackstardlb.insta.bot.data.discord.DiscordServerDataDTO;
import nl.blackstardlb.insta.bot.service.domain.DiscordServer;
import nl.blackstardlb.insta.bot.service.domain.DiscordServerPermission;
import org.javacord.api.DiscordApi;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.inject.Inject;
import java.util.List;

@Mapper(componentModel = "jsr330", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class DiscordServerDataMapper {
    @Inject
    private DiscordApi discordApi;

    @Mapping(target = "botInServer", expression = "java(isBotInServer(discordServerDataDTO))")
    public abstract DiscordServer toDiscordServer(DiscordServerDataDTO discordServerDataDTO);

    boolean isBotInServer(DiscordServerDataDTO discordServerDataDTO) {
        return discordApi.getServerById(discordServerDataDTO.getId()).isPresent();
    }

    List<DiscordServerPermission> toPermissions(Long permissions) {
        return DiscordServerPermission.allPermissionsFor(permissions);
    }
}
