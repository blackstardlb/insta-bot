export interface BaseDiscordServer {
    id: string;
    botInServer: boolean;
}

export interface FullDiscordServer extends BaseDiscordServer {
    channels: DiscordChannel[];
    botEnabled: boolean;
    selectedChannelId: boolean;
    name: string;
    instagramName: string;
    canTalk: boolean;
}

export interface DiscordChannel {
    id: string;
    name: string;
}
