package nl.blackstardlb.insta.bot;

import nl.blackstardlb.insta.bot.service.domain.DiscordAuthentication;

public interface UserFixture {
    static DiscordAuthentication defaultUser(String token) {
        DiscordAuthentication discordAuthentication = new DiscordAuthentication();
        discordAuthentication.setToken(token);
        return discordAuthentication;
    }
}
