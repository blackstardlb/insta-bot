package nl.blackstardlb.insta.bot.data.instagram.parsers;

import io.micronaut.test.annotation.MicronautTest;
import nl.blackstardlb.insta.bot.data.instagram.utils.TestUtils;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@MicronautTest
class QueryHashParserTest {
    @Inject
    QueryHashParser queryHashParser;

    @Test
    void getQueryHashFromScript() throws URISyntaxException, IOException {
        String queryHashScript = TestUtils.readFile("test.profile-page-container.js");
        String hashFromScript = queryHashParser.getQueryHashFromScript(queryHashScript);
        assertThat(hashFromScript).isEqualTo("2c5d4d8b70cad329c4a6ebe3abb6eedd");
    }
}
