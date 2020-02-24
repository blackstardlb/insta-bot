package nl.blackstardlb.insta.bot.data.instagram.models.response.shareddata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfilePage {
    @JsonProperty("graphql")
    private GraphQL graphQL;
}
