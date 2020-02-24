package nl.blackstardlb.insta.bot.data.discord;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;

import java.util.List;
import java.util.Map;

@Client("discord")
public abstract class DiscordClient {
    private final ObjectMapper objectMapper;

    protected DiscordClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // TODO fix this when theres a solution for https://github.com/micronaut-projects/micronaut-core/issues/1853
    @Post(value = "oauth2/token", produces = MediaType.APPLICATION_FORM_URLENCODED, consumes = MediaType.APPLICATION_JSON)
    protected abstract TokenResponseBody exchangeToken(@Body Map tokenRequestBody);

    public TokenResponseBody exchangeToken(TokenRequestBody tokenRequestBody) {
        Map map = objectMapper.convertValue(tokenRequestBody, Map.class);
        return exchangeToken(map);
    }

    @Get("users/@me")
    public abstract DiscordUser getDiscordUser(@Header(HttpHeaders.AUTHORIZATION) String accessToken);

    @Get("users/@me/guilds")
    @Retryable(attempts = "2", delay = "1s")
    public abstract List<DiscordServerDataDTO> getDiscordServers();
}
