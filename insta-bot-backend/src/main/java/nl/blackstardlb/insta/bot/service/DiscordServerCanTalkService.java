package nl.blackstardlb.insta.bot.service;

import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import javax.inject.Singleton;

@Singleton
public class DiscordServerCanTalkService {
    private final DiscordApi discordApi;

    private final NotificationRequestService notificationRequestService;

    public DiscordServerCanTalkService(DiscordApi discordApi, NotificationRequestService notificationRequestService) {
        this.discordApi = discordApi;
        this.notificationRequestService = notificationRequestService;
    }

    public boolean canTalk(Server server, NotificationRequest notificationRequest) {
        User bot = discordApi.getYourself();
        return server.getTextChannelById(notificationRequest.getDiscordChanelId())
                     .map(channel -> channel.canWrite(bot))
                     .orElse(false);
    }

    public boolean canTalk(String serverId) {
        return discordApi.getServerById(serverId).map(this::canTalk).orElse(false);
    }

    public boolean canTalk(Server server) {
        return notificationRequestService.getByDiscordId(server.getIdAsString())
                                         .map(notificationRequest -> canTalk(server, notificationRequest))
                                         .orElse(false);
    }

    public boolean canTalk(long serverId) {
        return discordApi.getServerById(serverId).map(this::canTalk).orElse(false);
    }
}
