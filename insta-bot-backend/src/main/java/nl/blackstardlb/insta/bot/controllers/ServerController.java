package nl.blackstardlb.insta.bot.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.config.ServerManager;
import nl.blackstardlb.insta.bot.controllers.dto.BaseDiscordServerDTO;
import nl.blackstardlb.insta.bot.controllers.dto.FullDiscordServerDTO;
import nl.blackstardlb.insta.bot.controllers.dto.FullServerRequestDTO;
import nl.blackstardlb.insta.bot.controllers.mappers.FullDiscordServerDTOMapper;
import nl.blackstardlb.insta.bot.service.DiscordServerUpdaterService;
import nl.blackstardlb.insta.bot.service.DiscordService;
import nl.blackstardlb.insta.bot.service.domain.FullDiscordServer;
import org.javacord.api.DiscordApi;

import javax.validation.Valid;
import java.util.Optional;

@Controller("api/servers")
@Slf4j
public class ServerController {
    private final DiscordApi discordApi;
    private final DiscordService discordService;
    private final FullDiscordServerDTOMapper fullDiscordServerDTOMapper;
    private final DiscordServerUpdaterService discordServerUpdaterService;

    public ServerController(DiscordApi discordApi, DiscordService discordService, FullDiscordServerDTOMapper fullDiscordServerDTOMapper, DiscordServerUpdaterService discordServerUpdaterService) {
        this.discordApi = discordApi;
        this.discordService = discordService;
        this.fullDiscordServerDTOMapper = fullDiscordServerDTOMapper;
        this.discordServerUpdaterService = discordServerUpdaterService;
    }

    @SuppressWarnings("rawtypes")
    @Get("/{id}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @ServerManager
    public HttpResponse<BaseDiscordServerDTO> server(@PathVariable String id) {
        Optional<FullDiscordServer> discordServer = discordService.getDiscordServer(id);
        if (!discordServer.isPresent()) {
            Optional<BaseDiscordServerDTO> botNotInDiscordServerDTO = discordService.getBotNotInDiscordServer(id)
                                                                                    .map(fullDiscordServerDTOMapper::toBotNotInDiscordServerDTO);
            return HttpResponseUtils.fromOptional(botNotInDiscordServerDTO);
        } else {
            return HttpResponse.ok(fullDiscordServerDTOMapper.toFullDiscordServerDTO(discordServer.get()));
        }
    }

    @Post(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @ServerManager
    public HttpResponse<FullDiscordServerDTO> updateServer(@PathVariable String id, @Valid @Body FullServerRequestDTO fullServerRequestDTO) {
        Optional<FullDiscordServer> discordServer = discordService.getDiscordServer(id);

        FullDiscordServer fullDiscordServer = fullDiscordServerDTOMapper.toFullDiscordServer(
                fullServerRequestDTO,
                discordServer.orElseGet(() -> FullDiscordServer.builder().id(Long.parseLong(id)).build())
        );
        return HttpResponse.ok(
                fullDiscordServerDTOMapper.toFullDiscordServerDTO(discordServerUpdaterService.save(fullDiscordServer))
        );
    }
}
