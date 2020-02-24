package nl.blackstardlb.insta.bot.data.instagram.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.blackstardlb.insta.bot.data.instagram.models.response.shareddata.SharedData;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Optional;

@Singleton
public class InstagramIdParser {
    private static final String WINDOW_SHARED_DATA = "window._sharedData = ";
    private final ObjectMapper objectMapper;

    public InstagramIdParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public long getIdFromScriptElements(Elements scripts) {
        Optional<SharedData> sharedData = scripts.stream()
                                                 .filter(script -> script.html().startsWith(WINDOW_SHARED_DATA))
                                                 .findFirst()
                                                 .map(this::getSharedDataJsonString)
                                                 .map(this::toSharedData);
        return sharedData.flatMap(s -> s.getEntryData().getProfilePages().stream().findFirst())
                         .map(p -> p.getGraphQL().getUser().getId())
                         .orElseThrow(RuntimeException::new);
    }

    private String getSharedDataJsonString(Element element) {
        return StringUtils.substringBetween(element.html(), WINDOW_SHARED_DATA, ";");
    }

    private SharedData toSharedData(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, SharedData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
