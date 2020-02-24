package nl.blackstardlb.insta.bot.controllers.dto;

import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.hateoas.AbstractResource;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class AddLinksInterceptor implements MethodInterceptor<Object, Object> {
    private final LinkAdder linkAdder;

    public AddLinksInterceptor(LinkAdder linkAdder) {
        this.linkAdder = linkAdder;
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {
        Object result = context.proceed();
        addLinkIfAbstractResource(result);

        if (result instanceof HttpResponse) {
            Object body = ((HttpResponse) result).body();
            addLinkIfAbstractResource(body);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private void addLinkIfAbstractResource(Object result) {
        if (result instanceof AbstractResource) {
            AbstractResource resource = (AbstractResource) result;
            linkAdder.addLinkTo(resource);
        }
    }
}
