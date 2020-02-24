package nl.blackstardlb.insta.bot.service;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import nl.blackstardlb.insta.bot.service.domain.DiscordTextChannel;
import nl.blackstardlb.insta.bot.service.domain.FullDiscordServer;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.server.Server;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
@Disabled
class NotificationDispatcherServiceTest {
    @Inject
    NotificationDispatcherService notificationDispatcherService;

    @Inject
    DiscordApi discordApi;

    @Inject
    DiscordService discordService;

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    void sendNotification() {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                                                                     .isEnabled(true)
                                                                     .discordChanelId("648879137640939500")
                                                                     .discordServerId("626670741764440075")
                                                                     .build();

        LastInstagramPost lastInstagramPost = LastInstagramPost.builder()
                                                               .createdAt(Instant.now())
                                                               .id("B5eG8Efp2DA")
                                                               .build();
        notificationDispatcherService.sendNotification(notificationRequest, lastInstagramPost);
    }

    @Test
    void channels() {
        String serverId = "626670741764440075";
        Optional<Server> serverById = discordApi.getServerById(serverId);
        List<Long> channelIds = serverById.map(server -> server.getTextChannels()
                                                               .stream()
                                                               .map(DiscordEntity::getId)
                                                               .collect(Collectors.toList())).get();

        Optional<FullDiscordServer> discordServer = discordService.getDiscordServer(serverId);
        List<Long> serviceIds = discordServer.map(fullDiscordServer -> fullDiscordServer.getChannels()
                                                                                        .stream()
                                                                                        .map(DiscordTextChannel::getId)
                                                                                        .collect(Collectors.toList()))
                                             .get();

        assertThat(channelIds).containsExactlyInAnyOrderElementsOf(serviceIds);
    }

    @SuppressWarnings("rawtypes")
    @Test
    void controllerChannels() {
        String serverId = "626670741764440075";
        Optional<Server> serverById = discordApi.getServerById(serverId);
        List<Long> channelIds = serverById.map(server -> server.getTextChannels()
                                                               .stream()
                                                               .map(DiscordEntity::getId)
                                                               .collect(Collectors.toList())).get();

        MutableHttpRequest<Object> httpRequest = HttpRequest.GET("/api/servers/" + serverId);
        httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer uwXqUC6ZhHUWEQ3RVfmiHkqG3If8At");

        FullDiscordServer fullDiscordServer = client.toBlocking().retrieve(httpRequest, FullDiscordServer.class);
        List<Long> controllerIds = fullDiscordServer.getChannels()
                                                    .stream()
                                                    .map(DiscordTextChannel::getId)
                                                    .collect(Collectors.toList());
        assertThat(channelIds).containsExactlyInAnyOrderElementsOf(controllerIds);

        System.out.println(channelIds);
        System.out.println(controllerIds);
        System.out.println(fullDiscordServer);
    }

    @Test
    void derp() {
        Optional<Channel> channelById = discordApi.getChannelById(626670741764440000L);
        System.out.println(channelById);
    }

    @Test
    void test() {
        Server server = discordApi.getServerById("420959252648165376").get();
        Channel channel = discordApi.getChannelById("420962707563347968").get();
        System.out.println(server);
        System.out.println(channel);
        boolean canWrite = channel.asTextChannel().get().canWrite(discordApi.getYourself());
        System.out.println("Can see: " + channel.canSee(discordApi.getYourself()));
        System.out.println("Can write: " + canWrite);
    }
}
