package nl.blackstardlb.insta.bot.controllers.mappers;

import nl.blackstardlb.insta.bot.controllers.dto.BotNotInDiscordServerDTO;
import nl.blackstardlb.insta.bot.controllers.dto.DiscordTextChannelDTO;
import nl.blackstardlb.insta.bot.controllers.dto.FullDiscordServerDTO;
import nl.blackstardlb.insta.bot.controllers.dto.FullServerRequestDTO;
import nl.blackstardlb.insta.bot.service.domain.BotNotInDiscordServer;
import nl.blackstardlb.insta.bot.service.domain.DiscordTextChannel;
import nl.blackstardlb.insta.bot.service.domain.FullDiscordServer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "jsr330", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class FullDiscordServerDTOMapper {
    @Mapping(target = "botInServer", expression = "java(true)")
    public abstract FullDiscordServerDTO toFullDiscordServerDTO(FullDiscordServer server);

    public abstract DiscordTextChannelDTO toDiscordTextChannelDTO(DiscordTextChannel discordTextChannel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "channels", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "canTalk", ignore = true)
    public abstract FullDiscordServer toFullDiscordServer(FullServerRequestDTO fullServerRequestDTO, @MappingTarget FullDiscordServer fullDiscordServer);

    @Mapping(target = "botInServer", expression = "java(false)")
    public abstract BotNotInDiscordServerDTO toBotNotInDiscordServerDTO(BotNotInDiscordServer botNotInDiscordServer);
}
