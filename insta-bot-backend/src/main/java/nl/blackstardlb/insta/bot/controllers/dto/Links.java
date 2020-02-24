package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.context.BeanDefinitionRegistry;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.HttpMethodMapping;
import io.micronaut.http.annotation.UriMapping;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.ExecutableMethod;
import nl.blackstardlb.insta.bot.controllers.UserController;
import nl.blackstardlb.insta.bot.exceptions.MethodNotFoundException;
import nl.blackstardlb.insta.bot.service.ServerPathResolver;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Singleton
public class Links {
    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private final ServerPathResolver serverPathResolver;

    public Links(BeanDefinitionRegistry beanDefinitionRegistry, ServerPathResolver serverPathResolver) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.serverPathResolver = serverPathResolver;
    }

    public Link mangedServers() {
        return getLink(UserController.class, "servers");
    }

    public Link me() {
        return getLink(UserController.class, "me");
    }

    private <T> Path getControllerPath(BeanDefinition<T> beanDefinition) {
        String part = Optional.ofNullable(beanDefinition.getAnnotation(Controller.class))
                              .flatMap(AnnotationValue::stringValue).orElse("");
        return Paths.get(part);
    }

    private <T> Path getMethodPath(ExecutableMethod<T, ?> method) {
        String part = Optional.ofNullable(method.getAnnotation(HttpMethodMapping.class))
                              .flatMap(AnnotationValue::stringValue).orElse(UriMapping.DEFAULT_URI);
        return Paths.get(part);
    }

    private <T> Path getPath(Class<T> controllerClass, String methodName, Object... args) {
        BeanDefinition<T> beanDefinition = beanDefinitionRegistry.getBeanDefinition(controllerClass);
        Optional<ExecutableMethod<T, ?>> method = beanDefinition.getExecutableMethods()
                                                                .stream()
                                                                .filter(m -> m.getMethodName().equals(methodName))
                                                                .findFirst();

        return getControllerPath(beanDefinition).resolve(toRelative(method.map(this::getMethodPath)
                                                                          .orElseThrow(MethodNotFoundException::new)));
    }

    private <T> Link getLink(Class<T> controllerClass, String methodName, Object... args) {
        Path path = toRelative(getPath(controllerClass, methodName, args));
        UriBuilder builder = getServerPath().path(path.toString());
        return Link.of(builder.build());
    }

    private UriBuilder getServerPath() {
        return UriBuilder.of(serverPathResolver.serverPath());
    }

    private Path toRelative(Path path) {
        Path relative = Paths.get("");

        for (int i = 0; i < path.getNameCount(); i++) {
            Path name = path.getName(i);
            relative = relative.resolve(name);
        }
        return relative;
    }
}
