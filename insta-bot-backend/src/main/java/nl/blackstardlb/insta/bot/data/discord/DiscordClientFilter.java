package nl.blackstardlb.insta.bot.data.discord;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;
import org.reactivestreams.Publisher;

import java.util.Objects;

@Filter(serviceId = "discord")
@Slf4j
public class DiscordClientFilter implements HttpClientFilter {
    private final SecurityService securityService;

    public DiscordClientFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        if (securityService.getAuthentication().isPresent()) {
            Authentication authentication = securityService.getAuthentication().get();
            if (authentication instanceof DiscordAuthentication) {
                DiscordAuthentication discordAuthentication = (DiscordAuthentication) authentication;
                request.header(HttpHeaders.AUTHORIZATION, "Bearer " + discordAuthentication.getToken());
            }
        }
        return chain.proceed(request);
    }
}
