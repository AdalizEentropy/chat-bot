package ru.adaliza.service;

import static java.util.UUID.randomUUID;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.adaliza.model.WebMessage;
import ru.adaliza.model.WebMessageRole;
import ru.adaliza.model.WebRequest;

@Slf4j
@Service
public class BaseWebService implements WebService {
    private final WebClient webClient;

    public BaseWebService(@Qualifier("baseWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    private static WebMessage createProductCategorySysMessage() {
        return new WebMessage(WebMessageRole.SYSTEM, "Называй только продовольственные категории");
    }

    public Flux<String> getProductCategory(String product) {
        String requestId = String.valueOf(randomUUID());
        log.debug("Get product category: reqId={}, product={}", requestId, product);

        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .header("X-Request-ID", requestId)
                .bodyValue(
                        new WebRequest(
                                List.of(
                                        createProductCategorySysMessage(),
                                        new WebMessage(WebMessageRole.USER, product))))
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(String.class));
    }
}
