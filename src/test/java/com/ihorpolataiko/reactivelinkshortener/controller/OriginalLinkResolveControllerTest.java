package com.ihorpolataiko.reactivelinkshortener.controller;

import com.ihorpolataiko.reactivelinkshortener.domain.OriginalLink;
import com.ihorpolataiko.reactivelinkshortener.domain.ShortenedLink;
import com.ihorpolataiko.reactivelinkshortener.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = OriginalLinkResolveController.class)
class OriginalLinkResolveControllerTest {

    private static final String ORIGINAL_LINK = "/original/link";

    private static final String SHORTENED_LINK = "shortened";

    private static final String NOT_EXISTING_SHORTENED_LINK = "not-existing";

    @MockBean
    private LinkService linkService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void convertToOriginalLinkFound() {

        when(linkService.convertToOriginal(new ShortenedLink(SHORTENED_LINK)))
                .thenReturn(Mono.just(new OriginalLink(ORIGINAL_LINK)));

        webTestClient.get()
                .uri("/" + SHORTENED_LINK)
                .exchange()
                .expectStatus()
                .isPermanentRedirect()
                .expectHeader()
                .valueEquals("Location", ORIGINAL_LINK);

        verify(linkService, only()).convertToOriginal(new ShortenedLink(SHORTENED_LINK));

    }

    @Test
    void convertToOriginalLinkNotFound() {

        when(linkService.convertToOriginal(new ShortenedLink(NOT_EXISTING_SHORTENED_LINK)))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/" + NOT_EXISTING_SHORTENED_LINK)
                .exchange()
                .expectStatus()
                .isNotFound();

        verify(linkService, only()).convertToOriginal(new ShortenedLink(NOT_EXISTING_SHORTENED_LINK));
    }
}