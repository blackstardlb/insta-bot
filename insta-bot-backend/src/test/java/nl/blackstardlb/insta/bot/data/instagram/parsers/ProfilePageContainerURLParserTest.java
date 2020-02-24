package nl.blackstardlb.insta.bot.data.instagram.parsers;

import io.micronaut.test.annotation.MicronautTest;
import nl.blackstardlb.insta.bot.data.instagram.utils.TestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@MicronautTest
class ProfilePageContainerURLParserTest {
    @Inject
    ProfilePageContainerURLParser profilePageContainerURLParser;

    @Test
    void getProfilePageContainerURL() throws IOException, URISyntaxException {
        String html = TestUtils.readFile("test.instagram.page.html");
        Document document = Jsoup.parse(html);
        String id = profilePageContainerURLParser.getProfilePageContainerURL(document.getElementsByTag("link"));
        assertThat(id).isEqualTo("/static/bundles/metro/ProfilePageContainer.js/8689829e6ad8.js");
    }
}
