package nl.blackstardlb.insta.bot.controllers;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
@Slf4j
public class AngularRedirector {
    private final ClassPathResourceLoader classPathResourceLoader;

    public AngularRedirector(ClassPathResourceLoader classPathResourceLoader) {
        this.classPathResourceLoader = classPathResourceLoader;
    }

    private static String streamToString(InputStream stream) {
        try {
            return IOUtils.toString(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse notFound(HttpRequest request) {
        log.info(String.valueOf(request));
        if (request.getHeaders()
                   .accept()
                   .stream()
                   .anyMatch(mediaType -> mediaType.getName().contains(MediaType.TEXT_HTML))) {

            Optional<String> index = classPathResourceLoader.getResourceAsStream("client/index.html")
                                                            .map(AngularRedirector::streamToString);
            if (index.isPresent()) {
                return HttpResponse.ok(index.get()).contentType(MediaType.TEXT_HTML);
            }
        }

        JsonError error = new JsonError("Page Not Found")
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError>notFound()
                .body(error);
    }
}
