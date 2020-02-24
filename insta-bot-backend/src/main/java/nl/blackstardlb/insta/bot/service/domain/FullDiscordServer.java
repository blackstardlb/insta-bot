package nl.blackstardlb.insta.bot.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullDiscordServer {
    private Long id;
    private List<DiscordTextChannel> channels;
    private boolean isBotEnabled;
    private String selectedChannelId;
    private String name;
    private String instagramName;
    private boolean canTalk;
}
