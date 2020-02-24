package nl.blackstardlb.insta.bot.controllers.mappers;

import nl.blackstardlb.insta.bot.controllers.dto.DiscordServerDTO;
import nl.blackstardlb.insta.bot.service.DiscordServerCanTalkService;
import nl.blackstardlb.insta.bot.service.ServerPathResolver;
import nl.blackstardlb.insta.bot.service.domain.DiscordServer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.inject.Inject;
import java.net.URI;

@Mapper(componentModel = "jsr330", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class DiscordServerMapper {
    @Inject
    private ServerPathResolver serverPathResolver;

    @Inject
    private DiscordServerCanTalkService discordServerCanTalkService;

    @Mapping(target = "iconURL", expression = "java(iconURL(discordServer))")
    @Mapping(target = "canTalk", expression = "java(canTalk(discordServer))")
    public abstract DiscordServerDTO toDiscordServerDTO(DiscordServer discordServer);

    public boolean canTalk(DiscordServer discordServer) {
        return discordServerCanTalkService.canTalk(discordServer.getId());
    }

    protected URI iconURL(DiscordServer discordServer) {
        if (discordServer.getIcon() != null) {
            return URI.create(String.format(
                    "https://cdn.discordapp.com/icons/%s/%s.png",
                    discordServer.getId(),
                    discordServer.getIcon()
            ));
        }
        return serverPathResolver.resolveFor("/images/default-server.jpg");
    }
}
