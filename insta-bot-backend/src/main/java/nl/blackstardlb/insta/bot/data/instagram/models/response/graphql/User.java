package nl.blackstardlb.insta.bot.data.instagram.models.response.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Optional;

@Data
@Introspected
public class User {
    @JsonProperty("edge_owner_to_timeline_media")
    private Optional<TimeLineMedia> timeLineMedia;
}
