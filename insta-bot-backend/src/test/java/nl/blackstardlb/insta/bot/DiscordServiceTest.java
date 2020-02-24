package nl.blackstardlb.insta.bot;

import io.micronaut.context.annotation.Property;
import io.micronaut.security.utils.DefaultSecurityService;
import io.micronaut.security.utils.SecurityService;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import nl.blackstardlb.insta.bot.data.discord.TokenResponseBody;
import nl.blackstardlb.insta.bot.service.DiscordService;
import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@Disabled
class DiscordServiceTest {
    @Inject
    DiscordService discordService;

    @Property(name = "test.discord.access_token")
    String accessToken;

    @Test
    void exchangeCode() {
        TokenResponseBody token = discordService.exchangeCode(
                "valid_code",
                "http://localhost:4200"
        );
    }

    @Test
    void getAuthentication() {
        DiscordAuthentication authentication = discordService.getAuthentication(accessToken);
        assertThat(authentication.getToken()).isEqualTo(accessToken);
        assertThat(authentication.getName()).isEqualTo("BlackStarDLB");
        assertThat(authentication.getDiscriminator()).isEqualTo("8058");
        assertThat(authentication.getId()).isEqualTo(131051405212385280L);
    }

    @Test
    void getUserDiscordServers() {
        discordService.getUserDiscordServers();
    }

    @MockBean(DefaultSecurityService.class)
    SecurityService discordClientFilter() {
        SecurityService securityServiceMock = mock(SecurityService.class);
        when(securityServiceMock.getAuthentication()).thenReturn(Optional.of(UserFixture.defaultUser(accessToken)));
        return securityServiceMock;
    }
}
