package nl.blackstardlb.insta.bot.config;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.reactivex.Maybe;
import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.service.DiscordService;
import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;
import org.reactivestreams.Publisher;

import java.util.Optional;


@Slf4j
public class DiscordAuthenticationFetcher implements AuthenticationFetcher {
    private final DiscordService discordService;

    public DiscordAuthenticationFetcher(DiscordService discordService) {
        this.discordService = discordService;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static <T> Maybe<T> fromOptional(Optional<T> optional) {
        return optional.map(Maybe::just).orElse(Maybe.empty());
    }

    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
        Optional<DiscordAuthentication> discordAuthentication = request.getHeaders()
                                                                       .getAuthorization()
                                                                       .map(auth -> auth.replaceFirst("Bearer ", ""))
                                                                       .map(discordService::getAuthentication);
        log.info(discordAuthentication.toString());
        return fromOptional(discordAuthentication)
                .map(auth -> (Authentication) auth)
                .toFlowable();
    }
}
