package nl.blackstardlb.insta.bot.data.instagram;

import io.micronaut.test.annotation.MicronautTest;
import nl.blackstardlb.insta.bot.data.instagram.models.request.QueryParams;
import nl.blackstardlb.insta.bot.data.instagram.models.request.QueryVariables;
import nl.blackstardlb.insta.bot.data.instagram.models.response.graphql.InstagramQueryResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;

@MicronautTest
class InstagramClientTest {
    @Inject
    InstagramClient instagramClient;

    @Test
    void getQueryResponse() {
        QueryVariables queryVariables = QueryVariables.builder().first(1).id(21494279).build();
        InstagramQueryResponse queryResponse = instagramClient.getQueryResponse(
                "2c5d4d8b70cad329c4a6ebe3abb6eedd",
                queryVariables
        );
        System.out.println("queryResponse = " + queryResponse);
        assertThat(queryResponse).isNotNull();
    }

    @Test
    @Disabled
    void getQueryParams() {
        QueryParams queryParams = instagramClient.getQueryParams("asmrglow");
        assertThat(queryParams.getId()).isEqualTo(21494279);
        assertThat(queryParams.getQueryHash()).isEqualTo("2c5d4d8b70cad329c4a6ebe3abb6eedd");
    }
}
