export interface ManageableServer {
    id: string;
    name: string;
    permissions: string[];
    botInServer: boolean;
    canTalk: boolean;
    owner: boolean;
    icon: string;
    iconURL: string;
}
