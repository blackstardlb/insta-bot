package nl.blackstardlb.insta.bot.data.instagram.models.response.shareddata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EntryData {
    @JsonProperty("ProfilePage")
    private List<ProfilePage> profilePages;
}
