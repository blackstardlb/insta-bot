package nl.blackstardlb.insta.bot.data.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiscordServerDataDTO {
    private String id;
    private String name;
    private String icon;
    @JsonProperty("owner")
    private boolean isOwner;
    private Long permissions;
}
