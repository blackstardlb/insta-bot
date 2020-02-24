package nl.blackstardlb.insta.bot.service;

import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import nl.blackstardlb.insta.bot.exceptions.ChannelDoesNotExistException;
import nl.blackstardlb.insta.bot.exceptions.ServerDoesNotExistException;
import nl.blackstardlb.insta.bot.service.domain.FullDiscordServer;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
public class DiscordServerUpdaterService {
    private final NotificationRequestService notificationRequestService;
    private final InstagramInformationService instagramInformationService;
    private final DiscordService discordService;
    private final DiscordApi discordApi;

    public DiscordServerUpdaterService(NotificationRequestService notificationRequestService, InstagramInformationService instagramInformationService, DiscordService discordService, DiscordApi discordApi) {
        this.notificationRequestService = notificationRequestService;
        this.instagramInformationService = instagramInformationService;
        this.discordService = discordService;
        this.discordApi = discordApi;
    }

    @Transactional
    public FullDiscordServer save(FullDiscordServer fullDiscordServer) {
        NotificationRequest notificationRequest = notificationRequestService.getByDiscordId(fullDiscordServer.getId()
                                                                                                             .toString())
                                                                            .orElseGet(() -> NotificationRequest.builder()
                                                                                                                .discordServerId(
                                                                                                                        fullDiscordServer
                                                                                                                                .getId()
                                                                                                                                .toString())
                                                                                                                .build());
        notificationRequest.setIsEnabled(fullDiscordServer.isBotEnabled());
        if (notificationRequest.getInstagramInformation() == null || !notificationRequest.getInstagramInformation()
                                                                                         .getInstagramName()
                                                                                         .equals(fullDiscordServer.getInstagramName())) {
            notificationRequest.setInstagramInformation(instagramInformationService.getOrSave(fullDiscordServer.getInstagramName()));
        }
        notificationRequest.setDiscordChanelId(fullDiscordServer.getSelectedChannelId());

        Optional<Server> serverById = discordApi.getServerById(notificationRequest.getDiscordServerId());
        Server server = serverById.orElseThrow(ServerDoesNotExistException::new);
        if (notificationRequest.getDiscordChanelId() != null) {
            server.getChannelById(notificationRequest.getDiscordChanelId())
                  .orElseThrow(ChannelDoesNotExistException::new);
        }
        notificationRequestService.save(notificationRequest);
        //noinspection OptionalGetWithoutIsPresent
        return discordService.getDiscordServer(fullDiscordServer.getId().toString()).get();
    }
}
