package nl.blackstardlb.insta.bot.service.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum DiscordServerPermission {
    CREATE_INSTANT_INVITE(0x00000001),
    KICK_MEMBERS(0x00000002),
    BAN_MEMBERS(0x00000004),
    ADMINISTRATOR(0x00000008),
    MANAGE_CHANNELS(0x00000010),
    MANAGE_GUILD(0x00000020),
    ADD_REACTIONS(0x00000040),
    VIEW_AUDIT_LOG(0x00000080),
    VIEW_CHANNEL(0x00000400),
    SEND_MESSAGES(0x00000800),
    SEND_TTS_MESSAGES(0x00001000),
    MANAGE_MESSAGES(0x00002000),
    EMBED_LINKS(0x00004000),
    ATTACH_FILES(0x00008000),
    READ_MESSAGE_HISTORY(0x00010000),
    MENTION_EVERYONE(0x00020000),
    USE_EXTERNAL_EMOJIS(0x00040000),
    CONNECT(0x00100000),
    SPEAK(0x00200000),
    MUTE_MEMBERS(0x00400000),
    DEAFEN_MEMBERS(0x00800000),
    MOVE_MEMBERS(0x01000000),
    USE_VAD(0x02000000),
    PRIORITY_SPEAKER(0x00000100),
    STREAM(0x00000200),
    CHANGE_NICKNAME(0x04000000),
    MANAGE_NICKNAMES(0x08000000),
    MANAGE_ROLES(0x10000000),
    MANAGE_WEBHOOKS(0x20000000),
    MANAGE_EMOJIS(0x40000000);

    private long permission;

    DiscordServerPermission(long permission) {
        this.permission = permission;
    }

    public static List<DiscordServerPermission> allPermissionsFor(long value) {
        return Arrays.stream(DiscordServerPermission.values())
                     .filter(permission -> permission.appliesTo(value))
                     .collect(
                             Collectors.toList());
    }

    public boolean appliesTo(long value) {
        return (value & permission) == permission;
    }
}
