package nl.blackstardlb.insta.bot.data.instagram.models.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Introspected
public class QueryParams {
    private String queryHash;
    private long id;
}
