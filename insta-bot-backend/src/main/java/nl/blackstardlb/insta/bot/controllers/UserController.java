package nl.blackstardlb.insta.bot.controllers;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.hateoas.Link;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.controllers.dto.*;
import nl.blackstardlb.insta.bot.controllers.mappers.DiscordServerMapper;
import nl.blackstardlb.insta.bot.controllers.mappers.DiscordUserMapper;
import nl.blackstardlb.insta.bot.service.DiscordService;
import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;
import nl.blackstardlb.insta.bot.service.domain.DiscordServer;

import java.util.List;
import java.util.stream.Collectors;

@Controller("api/users/me")
@Introspected
@Slf4j
public class UserController {
    private final DiscordService discordService;
    private final Links links;
    private final DiscordUserMapper discordUserMapper;
    private final DiscordServerMapper discordServerMapper;

    public UserController(DiscordService discordService, Links links, DiscordUserMapper discordUserMapper, DiscordServerMapper discordServerMapper) {
        this.links = links;
        this.discordService = discordService;
        this.discordUserMapper = discordUserMapper;
        this.discordServerMapper = discordServerMapper;
    }

    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @AddLinks
    HttpResponse<DiscordUserDTO> me(Authentication authentication) {
        if (authentication instanceof DiscordAuthentication) {
            DiscordUserDTO discordUserDTO = discordUserMapper.toDiscordUserDTO((DiscordAuthentication) authentication);
            return HttpResponse.ok(discordUserDTO);
        }
        return HttpResponse.badRequest();
    }

    @Get("manageable-servers")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    HttpResponse<Resources<DiscordServerDTO>> servers() {
        List<DiscordServer> userManageableDiscordServers = discordService.getUserManageableDiscordServers();
        List<DiscordServerDTO> serverDTOS = userManageableDiscordServers.stream()
                                                                        .map(discordServerMapper::toDiscordServerDTO)
                                                                        .collect(Collectors.toList());
        Resources<DiscordServerDTO> manageableServers = new Resources<>("manageableServers", serverDTOS);
        manageableServers.link(Link.SELF, links.mangedServers());
        manageableServers.link("for", links.me());
        return HttpResponse.ok(manageableServers);
    }
}
