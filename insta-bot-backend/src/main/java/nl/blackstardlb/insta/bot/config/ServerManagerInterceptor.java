package nl.blackstardlb.insta.bot.config;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.MutableArgumentValue;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;
import nl.blackstardlb.insta.bot.service.DiscordService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@Slf4j
public class ServerManagerInterceptor implements MethodInterceptor<Object, Object> {
    @Inject
    private DiscordService discordService;

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        log.info("WTF?");
        Optional<MutableArgumentValue<?>> idParamOptional = context.getParameters()
                                                                   .values()
                                                                   .stream()
                                                                   .filter(p -> p.getType()
                                                                                 .equals(String.class) && p.isAnnotationPresent(
                                                                           PathVariable.class))
                                                                   .findFirst();

        Optional<Boolean> isUserManager = idParamOptional.map(pId -> pId.getValue().toString())
                                                         .map(id -> discordService.isUserManagerIn(id));
        if (isUserManager.isPresent() && isUserManager.get()) {
            log.info("Manager");
            return context.proceed();
        } else {
            log.info("Not Manager");
            return HttpResponse.status(HttpStatus.UNAUTHORIZED);
        }
    }
}
