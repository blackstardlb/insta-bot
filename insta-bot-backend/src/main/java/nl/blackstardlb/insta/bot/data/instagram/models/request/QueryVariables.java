package nl.blackstardlb.insta.bot.data.instagram.models.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Introspected
public class QueryVariables {
    private long id;
    private int first;
}
