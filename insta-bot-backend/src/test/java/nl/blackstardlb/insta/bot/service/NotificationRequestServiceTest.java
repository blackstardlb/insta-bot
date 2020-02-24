package nl.blackstardlb.insta.bot.service;

import io.micronaut.test.annotation.MicronautTest;
import nl.blackstardlb.insta.bot.data.entities.InstagramInformation;
import nl.blackstardlb.insta.bot.data.entities.LastInstagramPost;
import nl.blackstardlb.insta.bot.data.entities.NotificationRequest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@MicronautTest
class NotificationRequestServiceTest {

    @Inject
    NotificationRequestService notificationRequestService;

    @Inject
    LastInstagramPostService lastInstagramPostService;

    @Inject
    DataSource dataSource;

    @Test
    void save() {
        InstagramInformation asmrglow = InstagramInformation.builder()
                                                            .instagramName("asmrdarling")
                                                            .build();
        NotificationRequest notificationRequest = NotificationRequest.builder()
                                                                     .discordServerId("626670741764440075")
                                                                     .discordChanelId("626670741764440077")
                                                                     .isEnabled(true)
                                                                     .instagramInformation(asmrglow)
                                                                     .build();
        NotificationRequest request = notificationRequestService.save(notificationRequest);
        assertThat(request.getInstagramInformation().getQueryHash()).isEqualTo("2c5d4d8b70cad329c4a6ebe3abb6eedd");
        assertThat(request.getInstagramInformation().getInstagramId()).isEqualTo(3060114195L);
        assertThat(request.getId()).isNotNull();
        assertThat(notificationRequestService.getNotificationRequests().size())
                .isGreaterThan(0);
    }

    @Test
    void updateLastInstagramPost() {
        LastInstagramPost lastInstagramPostToUpdate = LastInstagramPost.builder()
                                                                       .id("Test")
                                                                       .createdAt(Instant.now())
                                                                       .build();
        NotificationRequest notificationRequest = NotificationRequest.builder().id(1L).build();
        notificationRequestService.updateLastInstagramPost(notificationRequest, lastInstagramPostToUpdate);
        Optional<LastInstagramPost> lastInstagramPost = lastInstagramPostService.get("Test");
        assertThat(lastInstagramPost.isPresent()).isTrue();
    }
}
