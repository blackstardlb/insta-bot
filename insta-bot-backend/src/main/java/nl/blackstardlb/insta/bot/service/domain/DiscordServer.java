package nl.blackstardlb.insta.bot.service.domain;

import lombok.Data;

import java.util.List;

@Data
public class DiscordServer {
    private String id;
    private String name;
    private String icon;
    private boolean isOwner;
    private List<DiscordServerPermission> permissions;
    private boolean botInServer;

    public boolean isManageable() {
        return getPermissions().contains(DiscordServerPermission.MANAGE_GUILD);
    }
}
