package nl.blackstardlb.insta.bot.config;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import io.micronaut.security.filters.AuthenticationFetcher;
import nl.blackstardlb.insta.bot.service.DiscordService;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import javax.inject.Singleton;

@Factory
public class BeanFactory {
    @Property(name = "discord.api.token")
    private String token;

    @Singleton
    DiscordApi discordApi() {
        return new DiscordApiBuilder().setToken(token).login().join();
    }

    @Singleton
    AuthenticationFetcher authenticationFetcher(DiscordService discordService) {
        return new DiscordAuthenticationFetcher(discordService);
    }
}
