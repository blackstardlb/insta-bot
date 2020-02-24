package nl.blackstardlb.insta.bot.data.instagram.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface TestUtils {
    static String readFile(String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(TestUtils.class.getResource(String.format("/%s", fileName)).toURI());
        return String.join("\n", Files.readAllLines(path));
    }
}
