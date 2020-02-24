package nl.blackstardlb.insta.bot.controllers.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BotNotInDiscordServerDTO extends BaseDiscordServerDTO<FullDiscordServerDTO> {
}
