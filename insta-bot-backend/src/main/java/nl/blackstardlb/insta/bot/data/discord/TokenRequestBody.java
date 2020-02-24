package nl.blackstardlb.insta.bot.data.discord;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Introspected
@Builder
@Value
@Valid
public class TokenRequestBody {
    @JsonProperty("client_id")
    @NotNull
    private String clientId;

    @NotNull
    @JsonProperty("client_secret")
    private String clientSecret;

    @NotNull
    @JsonProperty("grant_type")
    private String grantType;

    @NotNull
    private String code;

    @NotNull
    @JsonProperty("redirect_uri")
    private String redirectURI;

    @NotNull
    private String scope;
}
