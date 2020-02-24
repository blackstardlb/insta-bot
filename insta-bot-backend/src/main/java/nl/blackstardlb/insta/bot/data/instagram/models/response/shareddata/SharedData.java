package nl.blackstardlb.insta.bot.data.instagram.models.response.shareddata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SharedData {
    @JsonProperty("entry_data")
    private EntryData entryData;
}

