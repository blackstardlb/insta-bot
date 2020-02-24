package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Introspected
public class FullServerRequestDTO {
    @NotNull
    private Boolean botEnabled;

    @NotNull
    @NotBlank
    private String selectedChannelId;

    @NotNull
    @NotBlank
    private String instagramName;
}
