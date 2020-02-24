package nl.blackstardlb.insta.bot.controllers;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import nl.blackstardlb.insta.bot.controllers.dto.LinkAdder;
import nl.blackstardlb.insta.bot.controllers.dto.TokenDTO;
import nl.blackstardlb.insta.bot.controllers.mappers.TokenMapper;
import nl.blackstardlb.insta.bot.data.discord.TokenResponseBody;
import nl.blackstardlb.insta.bot.service.DiscordService;

import javax.annotation.security.PermitAll;
import java.net.URL;

@Controller("api/auth")
@Introspected
public class AuthenticationController {
    private final LinkAdder linkAdder;
    private final DiscordService discordService;

    public AuthenticationController(LinkAdder linkAdder, DiscordService discordService) {
        this.linkAdder = linkAdder;
        this.discordService = discordService;
    }

    @Post("token")
    @PermitAll
    HttpResponse<TokenDTO> token(String code, URL redirectURL) {
        TokenResponseBody tokenResponseBody = discordService.exchangeCode(code, redirectURL.toString());
        TokenDTO tokenDTO = TokenMapper.INSTANCE.toTokenDTO(tokenResponseBody);
        linkAdder.addLinkTo(tokenDTO);
        return HttpResponse.ok(tokenDTO);
    }
}
