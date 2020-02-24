package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.context.BeanDefinitionRegistry;
import io.micronaut.context.env.DefaultPropertyPlaceholderResolver;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.convert.DefaultConversionService;
import io.micronaut.http.annotation.HttpMethodMapping;
import io.micronaut.http.hateoas.AbstractResource;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.uri.UriMatchTemplate;
import io.micronaut.inject.ExecutableMethod;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class LinkAdder {
    private final Links links;
    private final DefaultPropertyPlaceholderResolver resolver = new DefaultPropertyPlaceholderResolver(
            null,
            new DefaultConversionService()
    );

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public LinkAdder(Links links, BeanDefinitionRegistry beanDefinitionRegistry) {
        this.links = links;
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public <T extends AbstractResource> void addLinkTo(AbstractResource<T> resource) {
        Class<? extends AbstractResource> aClass = resource.getClass();
        log.info("Adding links for " + aClass);
        if (aClass.equals(TokenDTO.class)) {

        } else if (aClass.equals(DiscordUserDTO.class)) {
            resource.link(Link.SELF, links.me());
            resource.link("managedServers", links.mangedServers());
        }
    }

    private void getURI(ExecutableMethod m) {
        AnnotationValue<HttpMethodMapping> mappingAnnotation = m.getAnnotation(HttpMethodMapping.class);
        if (mappingAnnotation != null) {
            String uri = mappingAnnotation.getRequiredValue(String.class);

            List<DefaultPropertyPlaceholderResolver.Segment> segments = resolver.buildSegments(uri);
            StringBuilder uriValue = new StringBuilder();
            for (DefaultPropertyPlaceholderResolver.Segment segment : segments) {
                if (segment instanceof DefaultPropertyPlaceholderResolver.RawSegment) {
                    uriValue.append(segment.getValue(String.class));
                } else {
                    uriValue.append("tmp");
                }
            }

            UriMatchTemplate template = UriMatchTemplate.of(uriValue.toString());
//            RouteParameterElement[] parameters = Arrays.stream(element.getParameters())
//                                                       .map(RouteParameterElement::new)
//                                                       .toArray(RouteParameterElement[]::new);
        }
    }

}
