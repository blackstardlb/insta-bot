package nl.blackstardlb.insta.bot.service;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.controllers.dto.FullDiscordServerDTO;
import nl.blackstardlb.insta.bot.data.discord.*;
import nl.blackstardlb.insta.bot.service.domain.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class DiscordService {
    public static final String GRANT_TYPE = "authorization_code";
    private final DiscordClient discordClient;
    private final DiscordApi discordApi;
    private final DiscordServerDataMapper discordServerDataMapper;
    private final DiscordAuthenticationMapper discordAuthenticationMapper;
    private final FullDiscordServerMapper fullDiscordServerMapper;
    @Property(name = "discord.api.client-id")
    private String clientId;
    @Property(name = "discord.api.client-secret")
    private String clientSecret;
    @Property(name = "discord.api.scopes")
    private String[] scopes;

    public DiscordService(DiscordClient discordClient, DiscordApi discordApi, DiscordServerDataMapper discordServerDataMapper, DiscordAuthenticationMapper discordAuthenticationMapper, FullDiscordServerMapper fullDiscordServerMapper) {
        this.discordClient = discordClient;
        this.discordApi = discordApi;
        this.discordServerDataMapper = discordServerDataMapper;
        this.discordAuthenticationMapper = discordAuthenticationMapper;
        this.fullDiscordServerMapper = fullDiscordServerMapper;
    }

    public TokenResponseBody exchangeCode(String code, String redirectURL) {
        TokenRequestBody tokenRequestBody = TokenRequestBody.builder()
                                                            .clientId(clientId)
                                                            .clientSecret(clientSecret)
                                                            .grantType(GRANT_TYPE)
                                                            .scope(redirectURL)
                                                            .redirectURI(redirectURL)
                                                            .code(code)
                                                            .build();
        log.info(String.valueOf(tokenRequestBody));
        return discordClient.exchangeToken(tokenRequestBody);
    }

    public DiscordAuthentication getAuthentication(String accessToken) {
        DiscordUser discordUser = discordClient.getDiscordUser("Bearer " + accessToken);
        DiscordAuthentication discordAuthentication = discordAuthenticationMapper.toDiscordAuthentication(
                discordUser);
        discordAuthentication.setToken(accessToken);
        return discordAuthentication;
    }

    public List<DiscordServer> getUserDiscordServers() {
        List<DiscordServerDataDTO> discordServers = discordClient.getDiscordServers();
        return discordServers.stream()
                             .map(discordServerDataMapper::toDiscordServer)
                             .collect(Collectors.toList());
    }

    public List<DiscordServer> getUserManageableDiscordServers() {
        return getUserDiscordServers().stream()
                                      .filter(DiscordServer::isManageable)
                                      .collect(Collectors.toList());
    }

    public Optional<FullDiscordServer> getDiscordServer(String id) {
        Optional<Server> serverById = discordApi.getServerById(id);
        return serverById.map(fullDiscordServerMapper::toDiscordServer);
    }

    public Optional<BotNotInDiscordServer> getBotNotInDiscordServer(String id) {
        Optional<DiscordServer> serverOptional = getUserDiscordServers().stream()
                                                                        .filter(server -> server.getId().equals(id))
                                                                        .findFirst();
        return serverOptional.map(fullDiscordServerMapper::toBotNotInDiscordServer);
    }

    public boolean isUserManagerIn(String serverId) {
        return getUserDiscordServers()
                .stream()
                .anyMatch(s -> s.getId().equals(serverId));
    }
}

