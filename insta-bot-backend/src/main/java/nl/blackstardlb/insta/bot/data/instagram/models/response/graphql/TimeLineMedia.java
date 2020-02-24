package nl.blackstardlb.insta.bot.data.instagram.models.response.graphql;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.List;

@Data
@Introspected
public class TimeLineMedia {
    private List<NodeWrapper> edges;
}
