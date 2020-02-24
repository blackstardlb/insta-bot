package nl.blackstardlb.insta.bot.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.hateoas.AbstractResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Introspected
@Data
public class TokenDTO extends AbstractResource<TokenDTO> {
    private String accessToken;
    private String tokenType;
    private long expires_in;
    private String scope;
}
