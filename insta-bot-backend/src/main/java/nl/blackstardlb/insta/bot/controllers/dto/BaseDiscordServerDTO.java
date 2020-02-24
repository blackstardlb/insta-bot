package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.http.hateoas.AbstractResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("rawtypes")
@EqualsAndHashCode(callSuper = false)
@Data
public abstract class BaseDiscordServerDTO<T extends AbstractResource> extends AbstractResource<T> {
    private String id;
    private boolean botInServer;
    private String name;
}
