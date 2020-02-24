package nl.blackstardlb.insta.bot.data.instagram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import nl.blackstardlb.insta.bot.data.instagram.models.request.QueryParams;
import nl.blackstardlb.insta.bot.data.instagram.models.request.QueryVariables;
import nl.blackstardlb.insta.bot.data.instagram.models.response.graphql.InstagramQueryResponse;
import nl.blackstardlb.insta.bot.data.instagram.parsers.InstagramIdParser;
import nl.blackstardlb.insta.bot.data.instagram.parsers.ProfilePageContainerURLParser;
import nl.blackstardlb.insta.bot.data.instagram.parsers.QueryHashParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.nio.file.Paths;

@Client("instagram")
public abstract class InstagramClient {
    private final InstagramIdParser instagramIdParser;
    private final ObjectMapper objectMapper;
    private final ProfilePageContainerURLParser profilePageContainerURLParser;
    private final QueryHashParser queryHashParser;

    protected InstagramClient(InstagramIdParser instagramIdParser, ObjectMapper objectMapper, ProfilePageContainerURLParser profilePageContainerURLParser, QueryHashParser queryHashParser) {
        this.instagramIdParser = instagramIdParser;
        this.objectMapper = objectMapper;
        this.profilePageContainerURLParser = profilePageContainerURLParser;
        this.queryHashParser = queryHashParser;
    }

    @Get("${instagram.graphql}")
    protected abstract InstagramQueryResponse getQueryResponse(@QueryValue("query_hash") String queryHash, @QueryValue String variables);

    protected InstagramQueryResponse getQueryResponse(String queryHash, QueryVariables variables) {
        try {
            return this.getQueryResponse(queryHash, objectMapper.writeValueAsString(variables));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public InstagramQueryResponse getQueryResponse(QueryParams queryParams, int first) {
        return getQueryResponse(
                queryParams.getQueryHash(),
                QueryVariables.builder().id(queryParams.getId()).first(first).build()
        );
    }

    public InstagramQueryResponse getQueryResponse(QueryParams queryParams) {
        return this.getQueryResponse(queryParams, 1);
    }

    @Get("{instagramName}")
    protected abstract String getPage(String instagramName);

    public QueryParams getQueryParams(String instagramName) {
        String pageString = getPage(instagramName);
        Document document = Jsoup.parse(pageString);
        Elements scriptElements = document.getElementsByTag("script");
        long id = instagramIdParser.getIdFromScriptElements(scriptElements);
        String profileDirectoryLandingPageJSULR = profilePageContainerURLParser
                .getProfilePageContainerURL(document.getElementsByTag("link"));
        String fileName = Paths.get(profileDirectoryLandingPageJSULR)
                               .getFileName()
                               .toString();
        String script = getProfilePageContainer(fileName);
        String queryHash = queryHashParser.getQueryHashFromScript(script);
        return QueryParams.builder().id(id).queryHash(queryHash).build();
    }

    @Get("/static/bundles/metro/ProfilePageContainer.js/{fileName}")
    protected abstract String getProfilePageContainer(String fileName);
}
