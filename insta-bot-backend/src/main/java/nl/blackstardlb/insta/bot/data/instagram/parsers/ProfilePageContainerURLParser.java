package nl.blackstardlb.insta.bot.data.instagram.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.select.Elements;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class ProfilePageContainerURLParser {
    private static final String PROFILE_PAGE_CONTAINER = "/static/bundles/metro/ProfilePageContainer.js/";
    private final ObjectMapper objectMapper;

    public ProfilePageContainerURLParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getProfilePageContainerURL(Elements links) {
        String attr = "href";
        Optional<String> profileContainerURL = links.stream()
                                                    .filter(link -> link.hasAttr(attr) && link.attr(attr)
                                                                                              .startsWith(
                                                                                                      PROFILE_PAGE_CONTAINER))
                                                    .findFirst().map(it -> it.attr(attr));
        return profileContainerURL.orElseThrow(() -> new RuntimeException("No ProfileContainerURL Found"));
    }
}
