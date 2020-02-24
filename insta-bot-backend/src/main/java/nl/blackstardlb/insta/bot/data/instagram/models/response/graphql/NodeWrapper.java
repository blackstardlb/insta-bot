package nl.blackstardlb.insta.bot.data.instagram.models.response.graphql;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Optional;

@Data
@Introspected
public class NodeWrapper {
    private Optional<Node> node;
}
