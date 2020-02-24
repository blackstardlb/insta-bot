package nl.blackstardlb.insta.bot.service;

import io.micronaut.context.annotation.Property;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import nl.blackstardlb.insta.bot.exceptions.ChannelDoesNotExistException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

import javax.inject.Singleton;

@Singleton
public class NotificationDispatcherService {
    private final DiscordApi discordApi;
    private final NotificationRequestService notificationRequestService;

    @Property(name = "instagram.postBaseURL")
    private String postBaseURL;

    public NotificationDispatcherService(DiscordApi discordApi, NotificationRequestService notificationRequestService) {
        this.discordApi = discordApi;
        this.notificationRequestService = notificationRequestService;
    }

    public void sendNotification(NotificationRequest notificationRequest, LastInstagramPost newPost) {
        TextChannel textChannel = discordApi
                .getTextChannelById(notificationRequest.getDiscordChanelId())
                .orElseThrow(ChannelDoesNotExistException::new);
        textChannel.sendMessage(postBaseURL.concat(newPost.getId()))
                   .thenRun(() -> notificationRequestService.updateLastInstagramPost(notificationRequest, newPost));
    }
}
