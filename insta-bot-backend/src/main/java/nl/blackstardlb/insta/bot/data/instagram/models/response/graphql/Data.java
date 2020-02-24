package nl.blackstardlb.insta.bot.data.instagram.models.response.graphql;

import io.micronaut.core.annotation.Introspected;

import java.util.Optional;

@lombok.Data
@Introspected
public class Data {
    private Optional<User> user;
}
