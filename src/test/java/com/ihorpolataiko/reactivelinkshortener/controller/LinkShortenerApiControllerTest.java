package com.ihorpolataiko.reactivelinkshortener.controller;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.service.LinkService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = LinkShortenerApiController.class)
class LinkShortenerApiControllerTest {

    private static final OriginalLink ORIGINAL_LINK = new OriginalLink("/original/link");

    private static final ShortenedLink SHORTEN_LINK = new ShortenedLink("/shortened");

    @MockBean
    private LinkService linkService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @SneakyThrows
    void convertToShorten() {

        when(linkService.convertToShorten(ORIGINAL_LINK)).thenReturn(Mono.just(SHORTEN_LINK));

        webTestClient.post()
                .uri("/shorten-link")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(ORIGINAL_LINK)))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json(objectMapper.writeValueAsString(SHORTEN_LINK));

        verify(linkService, only()).convertToShorten(ORIGINAL_LINK);

    }
}