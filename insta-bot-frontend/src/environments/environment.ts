// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    production: false,
    discordAuthUrl: 'https://discordapp.com/api/oauth2/authorize?client_id=245924326249988096&redirect_uri=http%3A%2F%2Flocalhost%3A4200&response_type=code&scope=guilds%20identify',
    discordBotURL: 'https://discordapp.com/api/oauth2/authorize?client_id=245924326249988096&permissions=19456&redirect_uri=http%3A%2F%2Flocalhost%3A4200&scope=bot&guild_id=GUILD_ID',
    clientURL: 'http://localhost:4200',
    serverURL: 'http://localhost:8080/api',
    discordCookieName: 'discord-cookie'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
