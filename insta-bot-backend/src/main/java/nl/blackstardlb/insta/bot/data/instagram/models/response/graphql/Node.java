package nl.blackstardlb.insta.bot.data.instagram.models.response.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.time.Instant;

@Data
@Introspected
public class Node {
    private String shortcode;

    @JsonProperty("taken_at_timestamp")
    private Instant uploadedOn;
}
