package nl.blackstardlb.insta.bot.data.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Introspected
public class DiscordUser {
    private Long id;
    private String username;
    private String discriminator;
    private int flags;
    @JsonProperty("premium_type")
    private int premiumType;
    private String avatar;
}
