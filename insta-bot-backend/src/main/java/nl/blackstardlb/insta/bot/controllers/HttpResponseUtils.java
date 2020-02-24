package nl.blackstardlb.insta.bot.controllers;

import io.micronaut.http.HttpResponse;

import java.util.Optional;

public interface HttpResponseUtils {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <T> HttpResponse<T> fromOptional(Optional<T> optional) {
        return optional.map(HttpResponse::ok).orElse(HttpResponse.notFound());
    }
}
