package nl.blackstardlb.insta.bot.service.domain;

import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import nl.blackstardlb.insta.bot.service.DiscordServerCanTalkService;
import nl.blackstardlb.insta.bot.service.NotificationRequestService;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "jsr330", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class FullDiscordServerMapper {
    @Inject
    private DiscordServerCanTalkService discordServerCanTalkService;

    @Inject
    private NotificationRequestService notificationRequestService;

    @Mapping(target = "channels", source = "textChannels")
    @Mapping(target = "botEnabled", ignore = true)
    @Mapping(target = "selectedChannelId", ignore = true)
    @Mapping(target = "instagramName", ignore = true)
    @Mapping(target = "canTalk", expression = "java(false)")
    public abstract FullDiscordServer toDiscordServer(Server server);

    @AfterMapping
    protected void afterMapping(@MappingTarget FullDiscordServer fullDiscordServer, Server server) {
        Optional<NotificationRequest> byDiscordId = notificationRequestService.getByDiscordId(fullDiscordServer.getId()
                                                                                                               .toString());
        if (byDiscordId.isPresent()) {
            NotificationRequest notificationRequest = byDiscordId.get();
            fullDiscordServer.setSelectedChannelId(notificationRequest.getDiscordChanelId());
            fullDiscordServer.setBotEnabled(notificationRequest.getIsEnabled());
            fullDiscordServer.setInstagramName(notificationRequest.getInstagramInformation().getInstagramName());

            fullDiscordServer.setCanTalk(discordServerCanTalkService.canTalk(server, notificationRequest));
        }
    }

    public abstract DiscordTextChannel toDiscordTextChannel(ServerTextChannel serverTextChannel);

    List<DiscordServerPermission> toPermissions(Long permissions) {
        return DiscordServerPermission.allPermissionsFor(permissions);
    }

    public abstract BotNotInDiscordServer toBotNotInDiscordServer(DiscordServer discordServer);
}
