package nl.blackstardlb.insta.bot.service;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.runtime.server.EmbeddedServer;

import javax.inject.Singleton;
import java.net.URI;
import java.util.Optional;

@Singleton
public class ServerPathResolver {
    private final EmbeddedServer embeddedServer;

    public ServerPathResolver(EmbeddedServer embeddedServer) {
        this.embeddedServer = embeddedServer;
    }

    public URI resolveFor(String path) {
        return serverPath().resolve(path);
    }

    public URI serverPath() {
        return ServerRequestContext.currentRequest()
                                   .flatMap(this::pathFromHeaders)
                                   .orElse(embeddedServer.getURI());
    }

    private Optional<URI> pathFromHeaders(HttpRequest<Object> request) {
        return request.getHeaders()
                      .findFirst("X-Forwarded-Host")
                      .map(host -> request.getHeaders().findFirst("X-Forwarded-Proto").orElse("http") + "://" + host)
                      .flatMap(this::fromString);
    }

    private Optional<URI> fromString(String uri) {
        try {
            return Optional.of(URI.create(uri));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
