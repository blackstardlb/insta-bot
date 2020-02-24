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
class InstagramIdParserTest {
    @Inject
    InstagramIdParser instagramIdParser;

    @Test
    void getIdFromScriptElements() throws IOException, URISyntaxException {
        String html = TestUtils.readFile("test.instagram.page.html");
        Document document = Jsoup.parse(html);
        long id = instagramIdParser.getIdFromScriptElements(document.getElementsByTag("script"));
        assertThat(id).isEqualTo(21494279);
    }
}
