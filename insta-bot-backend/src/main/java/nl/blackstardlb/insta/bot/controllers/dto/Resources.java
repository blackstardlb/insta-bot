package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.http.hateoas.AbstractResource;
import io.micronaut.http.hateoas.Resource;

import java.util.List;
import java.util.stream.Collectors;

public class Resources<T extends Resource> extends AbstractResource<Resources> {
    public Resources(String name, List<T> embedables) {
        this.embedded(name, embedables.stream().map(it -> (Resource) it).collect(Collectors.toList()));
    }
}
