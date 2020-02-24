package nl.blackstardlb.insta.bot.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class FullDiscordServerDTO extends BaseDiscordServerDTO<FullDiscordServerDTO> {
    private List<DiscordTextChannelDTO> channels;
    private boolean isBotEnabled;
    private String selectedChannelId;
    private String instagramName;
    private boolean canTalk;
}


