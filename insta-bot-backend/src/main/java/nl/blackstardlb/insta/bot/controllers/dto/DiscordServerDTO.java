package nl.blackstardlb.insta.bot.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.blackstardlb.insta.bot.service.domain.DiscordServerPermission;

import java.net.URI;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class DiscordServerDTO extends BaseDiscordServerDTO<DiscordServerDTO> {
    private String icon;
    private boolean isOwner;
    private List<DiscordServerPermission> permissions;
    private URI iconURL;
    private boolean canTalk;
}
