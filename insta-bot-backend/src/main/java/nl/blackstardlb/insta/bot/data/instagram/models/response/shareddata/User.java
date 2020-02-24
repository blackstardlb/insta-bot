package nl.blackstardlb.insta.bot.data.instagram.models.response.shareddata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("full_name")
    private String fullName;

    private long id;
}
