package nl.blackstardlb.insta.bot.data.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Introspected
@Data
public class TokenResponseBody {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("tokenType")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expires_in;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("scope")
    private String scope;
}
